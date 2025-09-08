// application/usecases/CheckOutUseCase.java
package com.hotel.microservice.reserva.application.usecases;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.reserva.application.inputports.CheckOutInputPort;
import com.hotel.microservice.reserva.application.outputports.ReservaRepositorioPort;
import com.hotel.microservice.facturacion.application.inputports.EmitirFacturaHotelInputPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CheckOutUseCase implements CheckOutInputPort {
  private final ReservaRepositorioPort reservas;
  private final EmitirFacturaHotelInputPort emitirFactura;

  public CheckOutUseCase(ReservaRepositorioPort reservas,
                         EmitirFacturaHotelInputPort emitirFactura) {
    this.reservas = reservas;
    this.emitirFactura = emitirFactura;
  }

  @Override
  @Transactional
  public void checkOut(UUID id) {
    var r = reservas.porId(id).orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
    r.checkOut();// valida transición
      System.out.println("De nuevo en check out.......................");
    reservas.actualizarEstado(id, r.getEstado());
    reservas.flush();                      // <-- MUY IMPORTANTE: garantiza visibilidad

    // ahora sí, la lectura que hace facturación verá CHECKED_OUT
    emitirFactura.emitir(id, "GTQ", "A", "CF", "Consumidor Final");
  }
}