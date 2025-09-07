package com.restaurante.microservice.platillo.infrastructure.inputadapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PlatilloResponse(
        UUID id,
        UUID restauranteId,
        String nombre,
        String descripcion,
        BigDecimal precio,
        String imagenUrl,
        boolean disponible,
        boolean enabled,
        Instant createdAt,
        Instant updatedAt
        ) {

}
