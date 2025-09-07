// com/restaurante/microservice/facturacion/application/usecases/EmitirFacturaUseCase.java
package com.restaurante.microservice.facturacion.application.usecases;

//import com.restaurante.microservice.common.errors.BusinessRuleException;
import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.consumo.application.outputports.persistence.ConsumoRepositorioPort;
import com.restaurante.microservice.consumo.domain.ConsumoItem;
import com.restaurante.microservice.cuenta.application.ouputports.persistence.CuentaRepositorioPort;
import com.restaurante.microservice.cuenta.domain.Cuenta;
import com.restaurante.microservice.cuenta.domain.EstadoCuenta;
import com.restaurante.microservice.facturacion.application.inputports.EmitirFacturaInputPort;
import com.restaurante.microservice.facturacion.application.outputports.FacturaRepositorioPort;
//import com.restaurante.microservice.facturacion.application.outputports.persistence.FacturaRepositorioPort;
import com.restaurante.microservice.facturacion.domain.Factura;
import com.restaurante.microservice.facturacion.domain.FacturaItem;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class EmitirFacturaUseCase implements EmitirFacturaInputPort {

    private final FacturaRepositorioPort facturas;
    private final CuentaRepositorioPort cuentas;
    private final ConsumoRepositorioPort consumos;

    @Override
    public Factura emitir(UUID cuentaId, String moneda, String serie, String clienteNit, String clienteNombre) {
        // Idempotencia: si ya existe una factura para esa cuenta, devuelve la existente
        var existente = facturas.porCuentaId(cuentaId);
        if (existente.isPresent()) {
            return existente.get();
        }

        // 1) Validar cuenta COBRADA
        Cuenta cta = cuentas.porId(cuentaId).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        if (cta.getEstado() != EstadoCuenta.COBRADA) {
            throw new NotFoundException("La cuenta debe estar COBRADA para facturar");
        }

        // 2) Obtener items de consumo (snapshot)
        var itemsConsumo = consumos.listarPorCuenta(cuentaId, Pageable.unpaged()).getContent();
        List<FacturaItem> items = itemsConsumo.stream()
                .map(EmitirFacturaUseCase::toItem)
                .collect(Collectors.toList());

        // 3) Numeración (serie + número) con retry por colisión única
        int numero = nextNumeroWithRetry(cta.getRestauranteId(), serie, 5);

        // 4) Armar factura con snapshot de totales desde la Cuenta
        var f = Factura.emitir(
                cta.getRestauranteId(), cuentaId, moneda, serie, numero,
                (clienteNit == null || clienteNit.isBlank()) ? "CF" : clienteNit,
                (clienteNombre == null || clienteNombre.isBlank()) ? "Consumidor Final" : clienteNombre,
                cta.getSubtotal(), cta.getImpuesto(), cta.getPropina(), cta.getTotal(),
                items
        );

        // 5) Guardar
        try {
            return facturas.guardar(f);
        } catch (DataIntegrityViolationException ex) {
            // raro, pero reintenta con otro número
            numero = nextNumeroWithRetry(cta.getRestauranteId(), serie, 5);
            var retry = Factura.emitir(
                    cta.getRestauranteId(), cuentaId, moneda, serie, numero,
                    f.getClienteNit(), f.getClienteNombre(),
                    f.getSubtotal(), f.getImpuesto(), f.getPropina(), f.getTotal(), items
            );
            return facturas.guardar(retry);
        }
    }

    private static FacturaItem toItem(ConsumoItem it) {
        return FacturaItem.rehidratar(
                it.getId(), it.getNombre(), it.getPrecioUnitario(), it.getCantidad(), it.getSubtotal());
    }

    private int nextNumeroWithRetry(UUID restauranteId, String serie, int maxRetries) {
        for (int i = 0; i < maxRetries; i++) {
            int next = facturas.maxNumeroEnSerie(restauranteId, serie) + 1;
            if (next > 0) {
                return next;
            }
        }
        // en práctica no ocurre, pero evita bloqueo si el repo no responde
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }
}
