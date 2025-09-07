package com.restaurante.microservice.consumo.application.usecases;


import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.consumo.application.inputports.ActualizarConsumoInputPort;
import com.restaurante.microservice.consumo.application.outputports.persistence.ConsumoRepositorioPort;
import com.restaurante.microservice.consumo.application.outputports.query.RestauranteConsultaPort;
import com.restaurante.microservice.consumo.domain.ConsumoItem;
import com.restaurante.microservice.cuenta.application.ouputports.persistence.CuentaRepositorioPort;
import com.restaurante.microservice.cuenta.domain.Cuenta;
import com.restaurante.microservice.cuenta.domain.EstadoCuenta;

import java.math.BigDecimal;
import java.util.UUID;

public class ActualizarConsumoUseCase implements ActualizarConsumoInputPort {

    private final ConsumoRepositorioPort consumos;
    private final CuentaRepositorioPort cuentas;
    private final RestauranteConsultaPort restaurantes;

    public ActualizarConsumoUseCase(ConsumoRepositorioPort consumos,
            CuentaRepositorioPort cuentas,
            RestauranteConsultaPort restaurantes) {
        this.consumos = consumos;
        this.cuentas = cuentas;
        this.restaurantes = restaurantes;
    }

    @Override
    public ConsumoItem actualizar(UUID id, BigDecimal precioUnitario, Integer cantidad, String nota) {
        var item = consumos.porId(id).orElseThrow(() -> new NotFoundException("Ítem no encontrado"));
        var cuenta = cuentas.porId(item.getCuentaId()).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        if (cuenta.getEstado() != EstadoCuenta.ABIERTA) {
            throw new NotFoundException("La cuenta no está ABIERTA");
        }

        var actualizado = item.actualizar(precioUnitario, cantidad, nota);
        var saved = consumos.guardar(actualizado);

        recalcularTotales(cuenta);
        return saved;
    }

    private void recalcularTotales(Cuenta cuenta) {
        var subtotal = consumos.sumarSubtotalPorCuenta(cuenta.getId());
        var cfg = restaurantes.obtenerConfig(cuenta.getRestauranteId())
                .orElse(new RestauranteConsultaPort.ConfigRestaurante(BigDecimal.ZERO, BigDecimal.ZERO));
        var impuesto = subtotal.multiply(cfg.impuestoPorc());
        var propina = subtotal.multiply(cfg.propinaPorcDefault());
        cuenta.setTotales(subtotal, impuesto, propina);
        cuentas.guardar(cuenta);
    }
}
