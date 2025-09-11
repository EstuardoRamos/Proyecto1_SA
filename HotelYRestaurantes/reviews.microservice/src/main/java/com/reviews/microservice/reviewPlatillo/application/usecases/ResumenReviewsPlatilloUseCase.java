// application/usecases/ResumenReviewsPlatilloUseCase.java
package com.reviews.microservice.reviewPlatillo.application.usecases;

import java.util.UUID;

import com.reviews.microservice.common.AggResumen;
import com.reviews.microservice.reviewPlatillo.application.inputports.ResumenReviewsPlatilloInputPort;
import com.reviews.microservice.reviewPlatillo.application.outputports.persistence.ReviewPlatilloRepositorioPort;

public class ResumenReviewsPlatilloUseCase implements ResumenReviewsPlatilloInputPort {

    private final ReviewPlatilloRepositorioPort repo;

    public ResumenReviewsPlatilloUseCase(ReviewPlatilloRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public AggResumen resumen(UUID platilloId) {
        return repo.resumen(platilloId);
    }
}
