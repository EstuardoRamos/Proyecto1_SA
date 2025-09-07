package com.restaurante.microservice.platillo.infrastructure.inputadapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Petición para crear platillo")
public record CrearPlatilloRequest(
        @Schema(description = "Restaurante al que pertenece (opcional)", example = "null")
        UUID restauranteId,
        @NotBlank
        @Size(max = 150)
        @Schema(example = "Hamburguesa Clásica")
        String nombre,
        @Size(max = 500)
        @Schema(example = "Carne 120g, queso cheddar, lechuga, tomate.")
        String descripcion,
        @NotNull
        @DecimalMin("0.0")
        @Schema(example = "65.00")
        BigDecimal precio,
        @Schema(example = "https://cdn.ejemplo.com/menu/hamburguesa.jpg")
        String imagenUrl,
        @Schema(description = "Disponible en el menú", example = "true")
        Boolean disponible
        ) {

}
