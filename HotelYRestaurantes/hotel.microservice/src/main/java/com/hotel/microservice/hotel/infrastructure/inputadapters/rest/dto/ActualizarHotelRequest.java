package com.hotel.microservice.hotel.infrastructure.inputadapters.rest.dto;

public record ActualizarHotelRequest(
        String nombre,
        Integer estrellas,
        String pais,
        String ciudad,
        String linea1,
        String linea2,
        String codigoPostal,
        String checkInDesde,
        String checkOutHasta
        ) {

}
