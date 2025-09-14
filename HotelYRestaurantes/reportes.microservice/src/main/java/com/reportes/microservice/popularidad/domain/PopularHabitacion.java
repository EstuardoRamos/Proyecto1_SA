package com.reportes.microservice.popularidad.domain;

import java.util.List;
import java.util.UUID;

public final class PopularHabitacion implements PopularidadResultado {

    public record Top(UUID habitacionId, UUID hotelId, long alojamientos) {

    }

    public record ItemRanking(UUID habitacionId, long alojamientos) {

    }

    private final RangoFechas rango;
    private final UUID hotelId;
    private final Top top;
    private final List<ItemRanking> ranking;
    private final List<?> reservas; // dejamos DTOs en capa app

    public PopularHabitacion(RangoFechas rango, UUID hotelId, Top top,
            List<ItemRanking> ranking, List<?> reservas) {
        this.rango = rango;
        this.hotelId = hotelId;
        this.top = top;
        this.ranking = ranking;
        this.reservas = reservas;
    }

    public RangoFechas rango() {
        return rango;
    }

    public UUID hotelId() {
        return hotelId;
    }

    public Top top() {
        return top;
    }

    public List<ItemRanking> ranking() {
        return ranking;
    }

    public List<?> reservas() {
        return reservas;
    }
}
