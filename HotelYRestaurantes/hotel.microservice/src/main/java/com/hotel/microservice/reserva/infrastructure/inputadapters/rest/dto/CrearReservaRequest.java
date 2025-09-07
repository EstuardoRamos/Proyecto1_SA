package com.hotel.microservice.reserva.infrastructure.inputadapters.rest.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.UUID;

public record CrearReservaRequest(
        @NotNull
        UUID hotelId,
        @NotNull
        UUID habitacionId,
        @NotNull
        UUID clienteId,
        @NotNull
        LocalDate entrada,
        @NotNull
        LocalDate salida,
        @Min(1)
        int huespedes
        ) {

}
