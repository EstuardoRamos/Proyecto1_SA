// application/outputports/persistence/ReviewPlatilloRepositorioPort.java
package com.reviews.microservice.reviewPlatillo.application.outputports.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reviews.microservice.common.AggResumen;
import com.reviews.microservice.reviewPlatillo.domain.ReviewPlatillo;

public interface ReviewPlatilloRepositorioPort {

    ReviewPlatillo guardar(ReviewPlatillo r);

    Optional<ReviewPlatillo> porId(UUID id);

    Page<ReviewPlatillo> listarPorPlatillo(UUID platilloId, Pageable pageable);

    boolean existsByCuentaAndPlatillo(UUID cuentaId, UUID platilloId);

    AggResumen resumen(UUID platilloId);
}
