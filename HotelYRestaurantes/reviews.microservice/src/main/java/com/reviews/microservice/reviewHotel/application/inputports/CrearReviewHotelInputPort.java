// application/inputports/CrearReviewHotelInputPort.java
package com.reviews.microservice.reviewHotel.application.inputports;

import com.reviews.microservice.reviewHotel.domain.ReviewHotel;
import java.util.List;
import java.util.UUID;

public interface CrearReviewHotelInputPort {

    ReviewHotel crear(UUID facturaHotelId, int estrellas, String comentario, List<String> tags);
}
