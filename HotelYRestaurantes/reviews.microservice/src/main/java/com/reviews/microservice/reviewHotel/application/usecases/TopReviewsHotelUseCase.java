// TopReviewsHotelUseCase.java
package com.reviews.microservice.reviewHotel.application.usecases;

import com.reviews.microservice.reviewHotel.application.inputports.TopReviewsHotelInputPort;
import com.reviews.microservice.reviewHotel.application.outputports.persistence.ReviewHotelRepositorioPort;
import com.reviews.microservice.reviewHotel.domain.ReviewHotel;
import java.util.List;
import java.util.UUID;

public class TopReviewsHotelUseCase implements TopReviewsHotelInputPort {

    private final ReviewHotelRepositorioPort repo;

    public TopReviewsHotelUseCase(ReviewHotelRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public List<ReviewHotel> top(UUID hotelId, int limit) {
        return repo.top(hotelId, limit);
    }
}
