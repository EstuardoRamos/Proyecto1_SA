package com.restaurante.microservice.facturacion.application.usecases;

import com.restaurante.microservice.facturacion.application.inputports.ListarFacturasInputPort;
import com.restaurante.microservice.facturacion.application.outputports.FacturaRepositorioPort;
import com.restaurante.microservice.facturacion.domain.Factura;
import java.time.Instant;
import java.util.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListarFacturasUseCase implements ListarFacturasInputPort {

    private final FacturaRepositorioPort facturas;

    @Override
    public List<Factura> listar(java.util.UUID restauranteId, Instant desde, Instant hasta) {
        return facturas.listar(restauranteId, desde, hasta);
    }
}
