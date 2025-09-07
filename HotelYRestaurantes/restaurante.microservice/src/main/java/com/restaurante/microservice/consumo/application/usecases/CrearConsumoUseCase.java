package com.restaurante.microservice.consumo.application.usecases;

import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.consumo.application.inputports.CrearConsumoInputPort;
import com.restaurante.microservice.consumo.application.outputports.persistence.ConsumoRepositorioPort;
import com.restaurante.microservice.consumo.application.outputports.query.RestauranteConsultaPort;
import com.restaurante.microservice.consumo.domain.ConsumoItem;
import com.restaurante.microservice.cuenta.application.ouputports.persistence.CuentaRepositorioPort;
import com.restaurante.microservice.cuenta.domain.Cuenta;
import com.restaurante.microservice.cuenta.domain.EstadoCuenta;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CrearConsumoUseCase implements CrearConsumoInputPort {

    private final ConsumoRepositorioPort consumos;
    private final CuentaRepositorioPort cuentas;
    private final RestauranteConsultaPort restaurantes;

    @Override
    public ConsumoItem crear(UUID cuentaId, UUID platilloId, String nombre, BigDecimal precioUnitario, int cantidad, String nota) {
        var cuenta = cuentas.porId(cuentaId).orElseThrow(() -> new com.restaurante.microservice.common.errors.NotFoundException("Cuenta no encontrada"));
        if (cuenta.getEstado() != EstadoCuenta.ABIERTA) {
            throw new NotFoundException("La cuenta no está ABIERTA");
        }

        var item = ConsumoItem.crear(cuentaId, platilloId, nombre, precioUnitario, cantidad, nota);
        var saved = consumos.guardar(item);

        recalcularTotales(cuenta);
        return saved;
    }

    private void recalcularTotales(Cuenta cuenta) {
        var subtotal = consumos.sumarSubtotalPorCuenta(cuenta.getId());
        var cfg = restaurantes.obtenerConfig(cuenta.getRestauranteId())
                .orElse(new RestauranteConsultaPort.ConfigRestaurante(java.math.BigDecimal.ZERO, java.math.BigDecimal.ZERO));
        var imp = subtotal.multiply(cfg.impuestoPorc());
        var prop = subtotal.multiply(cfg.propinaPorcDefault());
        cuenta.setTotales(subtotal, imp, prop);
        cuentas.guardar(cuenta);
    }
}
