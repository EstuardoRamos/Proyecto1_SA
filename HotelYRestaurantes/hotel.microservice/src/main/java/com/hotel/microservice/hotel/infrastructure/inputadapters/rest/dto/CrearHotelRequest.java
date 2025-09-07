package com.hotel.microservice.hotel.infrastructure.inputadapters.rest.dto;

import jakarta.validation.constraints.*;

public record CrearHotelRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 180, message = "El nombre no debe exceder 180 caracteres")
        String nombre,
        @NotNull(message = "Las estrellas son obligatorias")
        @Min(value = 1, message = "Las estrellas deben ser entre 1 y 5")
        @Max(value = 5, message = "Las estrellas deben ser entre 1 y 5")
        Integer estrellas,
        @NotBlank(message = "El país es obligatorio")
        String pais,
        @NotBlank(message = "La ciudad es obligatoria")
        String ciudad,
        @NotBlank(message = "La dirección (línea 1) es obligatoria")
        String linea1,
        String linea2,
        @Size(max = 10, message = "El código postal no debe exceder 10 caracteres")
        String codigoPostal,
        @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "checkInDesde debe ser HH:mm")
        String checkInDesde,
        @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "checkOutHasta debe ser HH:mm")
        String checkOutHasta
        ) {

}
