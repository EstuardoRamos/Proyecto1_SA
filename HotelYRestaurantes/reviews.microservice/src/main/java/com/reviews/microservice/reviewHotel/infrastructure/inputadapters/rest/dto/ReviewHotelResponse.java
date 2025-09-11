// infrastructure/inputadapters/rest/dto/ReviewHotelResponse.java
package com.reviews.microservice.reviewHotel.infrastructure.inputadapters.rest.dto;

import com.reviews.microservice.reviewHotel.domain.ReviewHotel;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ReviewHotelResponse(
    UUID id, UUID facturaHotelId, UUID hotelId, UUID habitacionId, UUID clienteId,
    int estrellas, String comentario, List<String> tags, boolean enabled,
    Instant createdAt, Instant updatedAt
){
  public static ReviewHotelResponse from(ReviewHotel r){
    return new ReviewHotelResponse(
        r.getId(), r.getFacturaHotelId(), r.getHotelId(), r.getHabitacionId(), r.getClienteId(),
        r.getEstrellas(), r.getComentario(), r.getTags(), r.isEnabled(), r.getCreatedAt(), r.getUpdatedAt()
    );
  }
}