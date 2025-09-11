// application/inputports/ListarReviewsHotelInputPort.java
package com.reviews.microservice.reviewHotel.application.inputports;

import com.reviews.microservice.reviewHotel.domain.ReviewHotel;
import java.util.List;
import java.util.UUID;

public interface ListarReviewsHotelInputPort {

    List<ReviewHotel> porHotel(UUID hotelId);

    List<ReviewHotel> porFactura(UUID facturaHotelId);
}
