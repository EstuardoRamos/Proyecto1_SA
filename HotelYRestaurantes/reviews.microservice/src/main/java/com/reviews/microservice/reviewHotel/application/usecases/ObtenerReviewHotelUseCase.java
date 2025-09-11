// ObtenerReviewHotelUseCase.java
package com.reviews.microservice.reviewHotel.application.usecases;

import com.reviews.microservice.reviewHotel.application.inputports.ObtenerReviewHotelInputPort;
import com.reviews.microservice.reviewHotel.application.outputports.persistence.ReviewHotelRepositorioPort;
import com.reviews.microservice.reviewHotel.domain.ReviewHotel;
import java.util.Optional;
import java.util.UUID;

public class ObtenerReviewHotelUseCase implements ObtenerReviewHotelInputPort {
  private final ReviewHotelRepositorioPort repo;

  public ObtenerReviewHotelUseCase(ReviewHotelRepositorioPort repo) { this.repo = repo; }

  @Override public Optional<ReviewHotel> porId(UUID id) { return repo.porId(id); }
}