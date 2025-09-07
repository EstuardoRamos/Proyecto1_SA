// com/restaurante/microservice/facturacion/infrastructure/inputadapters/rest/dto/FacturaItemResponse.java
package com.restaurante.microservice.facturacion.infraestructure.inputadapters.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record FacturaItemResponse(UUID id, String nombre, BigDecimal precioUnitario, int cantidad, BigDecimal subtotal) {

}
