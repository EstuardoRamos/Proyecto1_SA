package com.hotel.microservice.reserva.infrastructure.inputadapters.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReservaResponse(
        UUID id, UUID hotelId, UUID habitacionId, UUID clienteId,
        LocalDate entrada, LocalDate salida, int huespedes,
        String estado, BigDecimal total
        ) {

}
