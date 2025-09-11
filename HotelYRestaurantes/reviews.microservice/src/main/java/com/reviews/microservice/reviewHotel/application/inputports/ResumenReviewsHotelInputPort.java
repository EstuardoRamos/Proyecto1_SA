// ResumenReviewsHotelInputPort.java
package com.reviews.microservice.reviewHotel.application.inputports;

public interface ResumenReviewsHotelInputPort {

    record Summary(long total, double promedio) {

    }

    Summary resumen(java.util.UUID hotelId);
}
