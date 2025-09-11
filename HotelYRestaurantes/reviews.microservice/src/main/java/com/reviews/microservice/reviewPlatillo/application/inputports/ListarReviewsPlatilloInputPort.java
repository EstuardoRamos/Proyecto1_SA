// application/inputports/ListarReviewsPlatilloInputPort.java
package com.reviews.microservice.reviewPlatillo.application.inputports;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reviews.microservice.reviewPlatillo.domain.ReviewPlatillo;

public interface ListarReviewsPlatilloInputPort {

    Page<ReviewPlatillo> porPlatillo(UUID platilloId, Pageable pageable);
}
