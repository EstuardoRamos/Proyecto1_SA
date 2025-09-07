// com/restaurante/microservice/facturacion/infrastructure/inputadapters/rest/dto/EmitirFacturaRequest.java
package com.restaurante.microservice.facturacion.infraestructure.inputadapters.rest.dto;

import java.util.UUID;

public record EmitirFacturaRequest(UUID cuentaId, String moneda, String serie,
        String clienteNit, String clienteNombre) {

}
