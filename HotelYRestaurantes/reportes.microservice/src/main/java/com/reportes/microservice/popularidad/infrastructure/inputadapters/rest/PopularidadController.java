// infrastructure/inputadapters/rest/PopularidadController.java
package com.reportes.microservice.popularidad.infrastructure.inputadapters.rest;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reportes.microservice.popularidad.application.dto.HabitacionPopularDTO;
import com.reportes.microservice.popularidad.application.dto.RestaurantePopularDTO;
import com.reportes.microservice.popularidad.application.inputports.CalcularHabitacionMasPopularPort;
import com.reportes.microservice.popularidad.application.inputports.CalcularRestauranteMasPopularPort;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://frontend-comerdormir.s3-website.us-east-2.amazonaws.com", allowCredentials = "true")
@RestController
@RequestMapping("/v1/reportes/popular")
public class PopularidadController {

    private final CalcularHabitacionMasPopularPort habUC;
    private final CalcularRestauranteMasPopularPort restUC;

    public PopularidadController(CalcularHabitacionMasPopularPort habUC,
            CalcularRestauranteMasPopularPort restUC) {
        this.habUC = habUC;
        this.restUC = restUC;
    }

    @Operation(summary = "Habitación más popular")
    @GetMapping("/habitacion")
    public HabitacionPopularDTO habitacion(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(required = false) UUID hotelId
    ) {
        return habUC.ejecutar(desde, hasta, hotelId);
    }

    @Operation(summary = "Restaurante más popular")
    @GetMapping("/restaurante")
    public RestaurantePopularDTO restaurante(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(required = false) UUID restauranteId
    ) {
        return restUC.ejecutar(desde, hasta, restauranteId);
    }
}
