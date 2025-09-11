// infrastructure/inputadapters/rest/dto/CrearReviewHotelRequest.java
package com.reviews.microservice.reviewHotel.infrastructure.inputadapters.rest.dto;

import jakarta.validation.constraints.*;
import java.util.List;
import java.util.UUID;

public record CrearReviewHotelRequest(
        @NotNull
        UUID facturaHotelId,
        @Min(1)
        @Max(5)
        int estrellas,
        String comentario,
        List<@Size(max = 50) String> tags
        ) {

}
