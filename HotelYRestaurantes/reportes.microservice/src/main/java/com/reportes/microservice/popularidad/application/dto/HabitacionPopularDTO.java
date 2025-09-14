package com.reportes.microservice.popularidad.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record HabitacionPopularDTO(
        LocalDate desde, LocalDate hasta, UUID hotelId,
        Top top, List<ItemRanking> ranking, List<Reserva> reservas
        ) {

    public record Top(UUID habitacionId, UUID hotelId, long alojamientos) {

    }

    public record ItemRanking(UUID habitacionId, long alojamientos) {

    }

    // application/dto/HabitacionPopularDTO.java (solo el anidado Reserva)
    public static record Reserva(
            java.util.UUID id,
            java.util.UUID hotelId,
            java.util.UUID habitacionId,
            java.util.UUID clienteId,
            java.time.LocalDate entrada,
            java.time.LocalDate salida,
            int huespedes,
            String estado,
            java.math.BigDecimal total
            ) {
        // Constructor de 6 parámetros con valores por defecto:

        public Reserva(java.util.UUID id,
                java.util.UUID hotelId,
                java.util.UUID habitacionId,
                java.util.UUID clienteId,
                java.time.LocalDate entrada,
                java.time.LocalDate salida) {
            this(id, hotelId, habitacionId, clienteId, entrada, salida,
                    0, null, java.math.BigDecimal.ZERO);
        }
    }
}
