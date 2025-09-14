package com.reportes.microservice.popularidad.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public final class PopularRestaurante implements PopularidadResultado {

    public record Top(UUID restauranteId, BigDecimal ingresos, long facturas) {

    }

    public record ItemRanking(UUID restauranteId, BigDecimal ingresos) {

    }

    private final RangoFechas rango;
    private final UUID restauranteIdFiltro;
    private final Top top;
    private final List<ItemRanking> ranking;
    private final List<?> facturas;

    public PopularRestaurante(RangoFechas rango, UUID restauranteIdFiltro, Top top,
            List<ItemRanking> ranking, List<?> facturas) {
        this.rango = rango;
        this.restauranteIdFiltro = restauranteIdFiltro;
        this.top = top;
        this.ranking = ranking;
        this.facturas = facturas;
    }

    public RangoFechas rango() {
        return rango;
    }

    public UUID restauranteIdFiltro() {
        return restauranteIdFiltro;
    }

    public Top top() {
        return top;
    }

    public List<ItemRanking> ranking() {
        return ranking;
    }

    public List<?> facturas() {
        return facturas;
    }
}
