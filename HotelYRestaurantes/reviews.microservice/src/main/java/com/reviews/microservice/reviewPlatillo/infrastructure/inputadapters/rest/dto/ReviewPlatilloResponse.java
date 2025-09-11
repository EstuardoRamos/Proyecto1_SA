// infrastructure/inputadapters/rest/dto/ReviewPlatilloResponse.java
package com.reviews.microservice.reviewPlatillo.infrastructure.inputadapters.rest.dto;

import java.time.Instant;
import java.util.UUID;

import com.reviews.microservice.reviewPlatillo.domain.ReviewPlatillo;

public record ReviewPlatilloResponse(
        UUID id, UUID cuentaId, UUID restauranteId, UUID platilloId, UUID clienteId,
        int estrellas, String comentario, boolean enabled, Instant createdAt, Instant updatedAt
        ) {

    public static ReviewPlatilloResponse from(ReviewPlatillo r) {
        return new ReviewPlatilloResponse(r.getId(), r.getCuentaId(), r.getRestauranteId(),
                r.getPlatilloId(), r.getClienteId(), r.getEstrellas(), r.getComentario(),
                r.isEnabled(), r.getCreatedAt(), r.getUpdatedAt());
    }
}
