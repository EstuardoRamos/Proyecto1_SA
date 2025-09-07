package com.hotel.microservice.hotel.infrastructure.inputadapters.rest.dto;

import java.util.UUID;

public record HotelResponse(
        UUID id, String nombre, int estrellas, boolean activo,
        String pais, String ciudad, String linea1, String linea2, String codigoPostal,
        String checkInDesde, String checkOutHasta
        ) {

}
