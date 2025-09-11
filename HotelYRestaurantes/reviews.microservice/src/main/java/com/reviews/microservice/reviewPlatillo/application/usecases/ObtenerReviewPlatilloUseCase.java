// application/usecases/ObtenerReviewPlatilloUseCase.java
package com.reviews.microservice.reviewPlatillo.application.usecases;

import java.util.Optional;
import java.util.UUID;

import com.reviews.microservice.reviewPlatillo.application.inputports.ObtenerReviewPlatilloInputPort;
import com.reviews.microservice.reviewPlatillo.application.outputports.persistence.ReviewPlatilloRepositorioPort;
import com.reviews.microservice.reviewPlatillo.domain.ReviewPlatillo;

public class ObtenerReviewPlatilloUseCase implements ObtenerReviewPlatilloInputPort {

    private final ReviewPlatilloRepositorioPort repo;

    public ObtenerReviewPlatilloUseCase(ReviewPlatilloRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Optional<ReviewPlatillo> porId(UUID id) {
        return repo.porId(id);
    }
}
