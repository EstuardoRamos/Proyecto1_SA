// application/outputports/query/FacturacionQueryPort.java
package com.reviews.microservice.reviewHotel.application.outputports.query;

import java.util.Optional;
import java.util.UUID;

public interface FacturacionQueryPort {

    record FacturaHotelSnapshot(UUID hotelId, UUID habitacionId, UUID clienteId, String estado) {

    }

    Optional<FacturaHotelSnapshot> facturaHotel(UUID facturaHotelId);
}
