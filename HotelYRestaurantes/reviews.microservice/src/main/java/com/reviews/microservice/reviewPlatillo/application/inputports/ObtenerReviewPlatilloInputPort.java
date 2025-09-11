// application/inputports/ObtenerReviewPlatilloInputPort.java
package com.reviews.microservice.reviewPlatillo.application.inputports;

import java.util.Optional;
import java.util.UUID;

import com.reviews.microservice.reviewPlatillo.domain.ReviewPlatillo;

public interface ObtenerReviewPlatilloInputPort {

    Optional<ReviewPlatillo> porId(UUID id);
}
