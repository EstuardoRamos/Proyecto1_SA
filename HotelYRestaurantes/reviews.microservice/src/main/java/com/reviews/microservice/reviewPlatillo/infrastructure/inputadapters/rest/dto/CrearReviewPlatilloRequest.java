// infrastructure/inputadapters/rest/dto/CrearReviewPlatilloRequest.java
package com.reviews.microservice.reviewPlatillo.infrastructure.inputadapters.rest.dto;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CrearReviewPlatilloRequest(
    @NotNull UUID cuentaId,
    @NotNull UUID platilloId,
    @Min(1) @Max(5) int estrellas,
    String comentario
) {}