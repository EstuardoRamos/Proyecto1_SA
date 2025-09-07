package com.restaurante.microservice.restaurante.infrastrucuture.inputadapters.rest.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public record CrearRestauranteRequest(
        UUID hotelId,
        @NotBlank
        @Size(max = 150)
        String nombre,
        @Size(max = 400)
        String direccion,
        @NotNull
        @DecimalMin("0.0")
        @DecimalMax("1.0")
        BigDecimal impuestoPorc,
        @NotNull
        @DecimalMin("0.0")
        @DecimalMax("1.0")
        BigDecimal propinaPorcDefault
        ) {

}
