package com.restaurante.microservice.restaurante.infrastrucuture.inputadapters.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record RestauranteResponse(
        UUID id, UUID hotelId, String nombre, String direccion,
        BigDecimal impuestoPorc, BigDecimal propinaPorcDefault,
        boolean enabled
        ) {

}
