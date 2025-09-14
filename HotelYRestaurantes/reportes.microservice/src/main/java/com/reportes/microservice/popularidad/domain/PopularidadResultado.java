package com.reportes.microservice.popularidad.domain;

public sealed interface PopularidadResultado
        permits PopularHabitacion, PopularRestaurante {
}
