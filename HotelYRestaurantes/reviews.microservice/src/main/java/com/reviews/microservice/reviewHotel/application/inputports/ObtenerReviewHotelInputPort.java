package com.reviews.microservice.reviewHotel.application.inputports;

// ObtenerReviewHotelInputPort.java


import java.util.Optional;
import java.util.UUID;

import com.reviews.microservice.reviewHotel.domain.ReviewHotel;

public interface ObtenerReviewHotelInputPort {
  Optional<ReviewHotel> porId(UUID id);
}