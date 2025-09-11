// TopReviewsHotelInputPort.java
package com.reviews.microservice.reviewHotel.application.inputports;

import com.reviews.microservice.reviewHotel.domain.ReviewHotel;
import java.util.List;
import java.util.UUID;

public interface TopReviewsHotelInputPort {

    List<ReviewHotel> top(UUID hotelId, int limit);
}
