// application/inputports/ObtenerPromedioHotelInputPort.java
package com.reviews.microservice.reviewHotel.application.inputports;

import java.util.UUID;

public interface ObtenerPromedioHotelInputPort {

    record Resultado(UUID hotelId, double promedio, long total) {

    }

    Resultado promedio(UUID hotelId);
}
