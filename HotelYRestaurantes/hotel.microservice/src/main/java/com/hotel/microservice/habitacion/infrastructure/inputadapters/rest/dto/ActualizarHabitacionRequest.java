package com.hotel.microservice.habitacion.infrastructure.inputadapters.rest.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ActualizarHabitacionRequest(
        @NotBlank
        String numero,
        @NotBlank
        String tipo,
        @Min(1)
        int capacidad,
        @NotNull
        @Positive
        BigDecimal precioBase,
        String descripcion
        ) {

}
