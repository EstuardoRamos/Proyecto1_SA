// application/inputports/CrearReviewPlatilloInputPort.java
package com.reviews.microservice.reviewPlatillo.application.inputports;

import java.util.UUID;

import com.reviews.microservice.reviewPlatillo.domain.ReviewPlatillo;

public interface CrearReviewPlatilloInputPort {

    ReviewPlatillo crear(UUID cuentaId, UUID platilloId, int estrellas, String comentario);
}
