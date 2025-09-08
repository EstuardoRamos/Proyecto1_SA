package com.hotel.microservice.facturacion.application.usecases;

//import com.hotel.microservice.common.errors.BusinessRuleException;
import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.facturacion.application.inputports.EmitirFacturaHotelInputPort;
import com.hotel.microservice.facturacion.application.outputports.persistence.FacturaHotelRepositorioPort;
import com.hotel.microservice.facturacion.application.outputports.query.ReservaHotelQueryPort;
import com.hotel.microservice.facturacion.domain.*;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

@RequiredArgsConstructor
public class EmitirFacturaHotelUseCase implements EmitirFacturaHotelInputPort {

  private final FacturaHotelRepositorioPort facturas;
  private final ReservaHotelQueryPort reservas;

  @Override
  public FacturaHotel emitir(UUID reservaId, String moneda, String serie, String clienteNit, String clienteNombre) {
    // idempotencia
    var ya = facturas.porReservaId(reservaId);
    if (ya.isPresent()) return ya.get();

    // pedir snapshot a Reserva
    var snap = reservas.snapshotFactura(reservaId).orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
    // regla: debe estar finalizada/checkout
    //if (!isFinalizada(snap.estadoReserva()))
     // throw new NotFoundException("La reserva debe estar finalizada para facturar");

    // numeración con retry simple
    int numero = siguienteNumero(snap.hotelId(), serie, 5);

    // mapear items
    var items = snap.items().stream()
        .map(i -> FacturaHotelItem.rehidratar(UUID.randomUUID(), i.descripcion(), i.precioUnitario(), i.cantidad(), i.subtotal()))
        .collect(Collectors.toList());

    var f = FacturaHotel.emitir(
        snap.hotelId(), snap.reservaId(),
        moneda == null ? (snap.moneda()==null? "GTQ" : snap.moneda()) : moneda,
        serie == null ? "A" : serie,
        numero,
        clienteNit, clienteNombre,
        snap.subtotal(), snap.impuesto(), snap.propina(), snap.total(),
        items
    );

    try {
      return facturas.guardar(f);
    } catch (DataIntegrityViolationException ex) {
      numero = siguienteNumero(snap.hotelId(), serie == null ? "A" : serie, 5);
      var retry = FacturaHotel.emitir(
          snap.hotelId(), snap.reservaId(),
          f.getMoneda(), f.getSerie(), numero,
          f.getClienteNit(), f.getClienteNombre(),
          f.getSubtotal(), f.getImpuesto(), f.getPropina(), f.getTotal(),
          items
      );
      return facturas.guardar(retry);
    }
  }

  private boolean isFinalizada(String estado) {
    if (estado == null) return false;
    var up = estado.toUpperCase();
    return up.contains("CHECKOUT") || up.contains("FINALIZA"); // ajusta a tu enum real
  }

  private int siguienteNumero(UUID hotelId, String serie, int retries) {
    String s = (serie == null ? "A" : serie);
    for (int i=0;i<retries;i++) {
      int n = facturas.maxNumeroEnSerie(hotelId, s) + 1;
      if (n > 0) return n;
    }
    return (int)(System.currentTimeMillis() % Integer.MAX_VALUE);
  }
}