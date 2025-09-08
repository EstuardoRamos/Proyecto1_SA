package com.hotel.microservice.facturacion.infrastructure.inputadapters.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record FacturaHotelItemResponse(
        UUID id, String descripcion, BigDecimal precioUnitario, int cantidad, BigDecimal subtotal
        ) {

}
