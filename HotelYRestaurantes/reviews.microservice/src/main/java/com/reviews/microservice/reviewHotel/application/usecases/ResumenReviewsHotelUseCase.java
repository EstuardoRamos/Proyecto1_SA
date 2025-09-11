// ResumenReviewsHotelUseCase.java
package com.reviews.microservice.reviewHotel.application.usecases;

import com.reviews.microservice.reviewHotel.application.inputports.ResumenReviewsHotelInputPort;
import com.reviews.microservice.reviewHotel.application.outputports.persistence.ReviewHotelRepositorioPort;
import java.util.UUID;

public class ResumenReviewsHotelUseCase implements ResumenReviewsHotelInputPort {
  private final ReviewHotelRepositorioPort repo;
  public ResumenReviewsHotelUseCase(ReviewHotelRepositorioPort repo){ this.repo = repo; }

  @Override public Summary resumen(UUID hotelId) {
    var agg = repo.resumen(hotelId);
    long total = agg.total();
    double avg = total == 0 ? 0.0 : agg.promedio();
    return new Summary(total, avg);
  }
}