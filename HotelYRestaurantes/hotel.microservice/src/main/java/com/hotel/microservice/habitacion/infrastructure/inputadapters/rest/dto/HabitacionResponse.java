package com.hotel.microservice.habitacion.infrastructure.inputadapters.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record HabitacionResponse(
        UUID id, UUID hotelId, String numero, String tipo,
        int capacidad, BigDecimal precioBase, String estado, String descripcion
        ) {

}
