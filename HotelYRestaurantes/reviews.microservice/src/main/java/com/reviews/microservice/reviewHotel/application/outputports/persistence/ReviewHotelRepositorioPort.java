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
    //ReviewHotel guardar(ReviewHotel r);
    Optional<ReviewHotel> porId(UUID id);

    //Page<ReviewHotel> listar(UUID hotelId, Boolean enabled, Pageable pageable);

    record Agg(long total, double promedio) {

    }

    Agg resumen(UUID hotelId);

    List<ReviewHotel> top(UUID hotelId, int limit);

    void setEnabled(UUID id, boolean enabled);
}
