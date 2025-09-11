// application/outputports/persistence/ReviewHotelRepositorioPort.java
package com.reviews.microservice.reviewHotel.application.outputports.persistence;

import com.reviews.microservice.reviewHotel.domain.ReviewHotel;
import java.util.*;

import java.util.UUID;

public interface ReviewHotelRepositorioPort {

    ReviewHotel guardar(ReviewHotel r);

    boolean existsByFacturaHotelId(UUID facturaHotelId);

    List<ReviewHotel> listarPorHotel(UUID hotelId);

    List<ReviewHotel> listarPorFactura(UUID facturaHotelId);

    double promedioPorHotel(UUID hotelId); // 0 si no hay

    long totalPorHotel(UUID hotelId);
}
