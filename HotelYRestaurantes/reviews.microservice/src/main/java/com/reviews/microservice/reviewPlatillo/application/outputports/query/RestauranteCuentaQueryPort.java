// application/outputports/query/RestauranteCuentaQueryPort.java
package com.reviews.microservice.reviewPlatillo.application.outputports.query;

import java.util.Optional;

public interface RestauranteCuentaQueryPort {

    Optional<CuentaSnapshot> cuenta(java.util.UUID cuentaId);

    boolean cuentaContienePlatillo(java.util.UUID cuentaId, java.util.UUID platilloId);

    record CuentaSnapshot(java.util.UUID cuentaId, java.util.UUID restauranteId, java.util.UUID clienteId) {

    }
}
