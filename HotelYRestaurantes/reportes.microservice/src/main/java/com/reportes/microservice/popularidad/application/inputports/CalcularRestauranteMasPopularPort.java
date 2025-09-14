package com.reportes.microservice.popularidad.application.inputports;

import java.time.LocalDate;
import java.util.UUID;

import com.reportes.microservice.popularidad.application.dto.RestaurantePopularDTO;

public interface CalcularRestauranteMasPopularPort {

    RestaurantePopularDTO ejecutar(LocalDate desde, LocalDate hasta, UUID restauranteId);
}
