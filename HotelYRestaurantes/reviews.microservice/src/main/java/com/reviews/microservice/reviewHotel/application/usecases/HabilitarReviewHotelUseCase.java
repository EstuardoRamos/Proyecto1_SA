// HabilitarReviewHotelUseCase.java
package com.reviews.microservice.reviewHotel.application.usecases;

import com.reviews.microservice.reviewHotel.application.inputports.HabilitarReviewHotelInputPort;
import com.reviews.microservice.reviewHotel.application.outputports.persistence.ReviewHotelRepositorioPort;
import java.util.UUID;

public class HabilitarReviewHotelUseCase implements HabilitarReviewHotelInputPort {

    private final ReviewHotelRepositorioPort repo;

    public HabilitarReviewHotelUseCase(ReviewHotelRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public void habilitar(UUID id) {
        repo.setEnabled(id, true);
    }
}
