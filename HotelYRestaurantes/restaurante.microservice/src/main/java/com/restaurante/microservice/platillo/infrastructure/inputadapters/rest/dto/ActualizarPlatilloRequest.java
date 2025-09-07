package com.restaurante.microservice.platillo.infrastructure.inputadapters.rest.dto;
//package com.restaurante.microservice.platillo.infrastructure.inputadapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ActualizarPlatilloRequest(
        @Size(max = 150)
        @Schema(example = "Hamburguesa Clásica (doble queso)")
        String nombre,
        @Size(max = 500)
        @Schema(example = "Carne 120g, doble cheddar, lechuga, tomate.")
        String descripcion,
        @DecimalMin("0.0")
        @Schema(example = "68.00")
        BigDecimal precio,
        @Schema(example = "https://cdn.ejemplo.com/menu/hamburguesa2.jpg")
        String imagenUrl,
        @Schema(example = "true")
        Boolean disponible
        ) {

}
