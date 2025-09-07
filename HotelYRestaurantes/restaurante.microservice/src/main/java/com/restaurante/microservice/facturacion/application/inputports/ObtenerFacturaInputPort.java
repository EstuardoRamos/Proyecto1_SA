// com/restaurante/microservice/facturacion/application/inputports/ObtenerFacturaInputPort.java
package com.restaurante.microservice.facturacion.application.inputports;

import com.restaurante.microservice.facturacion.domain.Factura;
import java.util.Optional;
import java.util.UUID;

public interface ObtenerFacturaInputPort {

    Optional<Factura> porId(UUID id);
}
