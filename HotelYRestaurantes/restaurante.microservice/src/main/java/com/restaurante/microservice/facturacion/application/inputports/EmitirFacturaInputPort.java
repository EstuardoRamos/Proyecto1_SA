// com/restaurante/microservice/facturacion/application/inputports/EmitirFacturaInputPort.java
package com.restaurante.microservice.facturacion.application.inputports;

import com.restaurante.microservice.facturacion.domain.Factura;
import java.util.UUID;

public interface EmitirFacturaInputPort {

    Factura emitir(UUID cuentaId, String moneda, String serie, String clienteNit, String clienteNombre);
}
