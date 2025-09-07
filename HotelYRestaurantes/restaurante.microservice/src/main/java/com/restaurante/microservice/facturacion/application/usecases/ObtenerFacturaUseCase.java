// com/restaurante/microservice/facturacion/application/usecases/ObtenerFacturaUseCase.java
package com.restaurante.microservice.facturacion.application.usecases;

import com.restaurante.microservice.facturacion.application.inputports.ObtenerFacturaInputPort;
import com.restaurante.microservice.facturacion.application.outputports.FacturaRepositorioPort;
//import com.restaurante.microservice.facturacion.application.outputports.persistence.FacturaRepositorioPort;
import com.restaurante.microservice.facturacion.domain.Factura;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObtenerFacturaUseCase implements ObtenerFacturaInputPort {

    private final FacturaRepositorioPort facturas;

    @Override
    public Optional<Factura> porId(UUID id) {
        return facturas.porId(id);
    }
}
