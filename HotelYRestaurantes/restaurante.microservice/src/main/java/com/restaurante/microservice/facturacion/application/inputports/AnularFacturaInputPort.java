// com/restaurante/microservice/facturacion/application/inputports/AnularFacturaInputPort.java
package com.restaurante.microservice.facturacion.application.inputports;

import com.restaurante.microservice.facturacion.domain.Factura;
import java.util.UUID;

public interface AnularFacturaInputPort {

    Factura anular(UUID facturaId);
}
