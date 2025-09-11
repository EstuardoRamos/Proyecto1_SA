// application/usecases/ListarReviewsPlatilloUseCase.java
package com.reviews.microservice.reviewPlatillo.application.usecases;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reviews.microservice.reviewPlatillo.application.inputports.ListarReviewsPlatilloInputPort;
import com.reviews.microservice.reviewPlatillo.application.outputports.persistence.ReviewPlatilloRepositorioPort;
import com.reviews.microservice.reviewPlatillo.domain.ReviewPlatillo;

public class ListarReviewsPlatilloUseCase implements ListarReviewsPlatilloInputPort {

    private final ReviewPlatilloRepositorioPort repo;

    public ListarReviewsPlatilloUseCase(ReviewPlatilloRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Page<ReviewPlatillo> porPlatillo(UUID platilloId, Pageable pageable) {
        return repo.listarPorPlatillo(platilloId, pageable);
    }
}
