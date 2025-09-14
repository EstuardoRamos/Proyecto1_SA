package com.reportes.microservice.popularidad.domain;

import java.time.LocalDate;
import java.util.Objects;

public record RangoFechas(LocalDate desde, LocalDate hasta) {

    public RangoFechas  {
        Objects.requireNonNull(desde);
        Objects.requireNonNull(hasta);
        if (hasta.isBefore(desde)) {
            throw new IllegalArgumentException("hasta < desde");
        }
    }
}
