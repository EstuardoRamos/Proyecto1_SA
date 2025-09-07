// com/restaurante/microservice/facturacion/application/inputports/ListarFacturasInputPort.java
package com.restaurante.microservice.facturacion.application.inputports;

import com.restaurante.microservice.facturacion.domain.Factura;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ListarFacturasInputPort {

    List<Factura> listar(UUID restauranteId, Instant desde, Instant hasta);
}
