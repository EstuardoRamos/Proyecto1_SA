// DeshabilitarReviewHotelUseCase.java
package com.reviews.microservice.reviewHotel.application.usecases;

import com.reviews.microservice.reviewHotel.application.inputports.DeshabilitarReviewHotelInputPort;
import com.reviews.microservice.reviewHotel.application.outputports.persistence.ReviewHotelRepositorioPort;
import java.util.UUID;

public class DeshabilitarReviewHotelUseCase implements DeshabilitarReviewHotelInputPort {

    private final ReviewHotelRepositorioPort repo;

    public DeshabilitarReviewHotelUseCase(ReviewHotelRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public void deshabilitar(UUID id) {
        repo.setEnabled(id, false);
    }
}
