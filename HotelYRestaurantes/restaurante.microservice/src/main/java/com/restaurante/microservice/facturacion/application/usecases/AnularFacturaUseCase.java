package com.restaurante.microservice.facturacion.application.usecases;

import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.facturacion.application.inputports.AnularFacturaInputPort;
import com.restaurante.microservice.facturacion.application.outputports.FacturaRepositorioPort;
//import com.restaurante.microservice.facturacion.application.outputports.persistence.FacturaRepositorioPort;
import com.restaurante.microservice.facturacion.domain.Factura;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AnularFacturaUseCase implements AnularFacturaInputPort {

    private final FacturaRepositorioPort facturas;

    @Override
    public Factura anular(UUID facturaId) {
        var f = facturas.porId(facturaId).orElseThrow(() -> new NotFoundException("Factura no encontrada"));
        return facturas.guardar(f.anular());
    }
}
