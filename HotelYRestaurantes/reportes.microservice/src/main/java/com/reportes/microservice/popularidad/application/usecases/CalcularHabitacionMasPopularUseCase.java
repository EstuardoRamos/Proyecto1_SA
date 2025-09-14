// application/usecases/CalcularHabitacionMasPopularUseCase.java
package com.reportes.microservice.popularidad.application.usecases;

import com.reportes.microservice.popularidad.application.dto.HabitacionPopularDTO;
import com.reportes.microservice.popularidad.application.inputports.CalcularHabitacionMasPopularPort;
import com.reportes.microservice.popularidad.application.outputports.ReservasQueryPort;
import com.reportes.microservice.popularidad.application.outputports.ReservasQueryPort.ReservaSnapshot;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CalcularHabitacionMasPopularUseCase implements CalcularHabitacionMasPopularPort {

    private final ReservasQueryPort reservas;

    public CalcularHabitacionMasPopularUseCase(ReservasQueryPort reservas) {
        this.reservas = reservas;
    }

    @Override
    public HabitacionPopularDTO ejecutar(LocalDate desde, LocalDate hasta, UUID hotelId) {
        List<ReservaSnapshot> snaps = reservas.listar(desde, hasta, hotelId);

        Map<UUID, Long> rankingMap = snaps.stream()
                .collect(Collectors.groupingBy(ReservaSnapshot::habitacionId, Collectors.counting()));

        if (rankingMap.isEmpty()) {
            return new HabitacionPopularDTO(desde, hasta, hotelId, null, List.of(), List.of());
        }

        var top = rankingMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();

        UUID habitacionTopId = top.getKey();

        List<HabitacionPopularDTO.Reserva> reservasTop = snaps.stream()
                .filter(r -> Objects.equals(r.habitacionId(), habitacionTopId))
                .map(r -> new HabitacionPopularDTO.Reserva(
                r.id(), r.hotelId(), r.habitacionId(), r.clienteId(), r.entrada(), r.salida()
        ))
                .toList();

        List<HabitacionPopularDTO.ItemRanking> ranking = rankingMap.entrySet().stream()
                .sorted(Map.Entry.<UUID, Long>comparingByValue().reversed())
                .map(e -> new HabitacionPopularDTO.ItemRanking(e.getKey(), e.getValue()))
                .toList();

        UUID hotelDelTop = reservasTop.isEmpty() ? hotelId : reservasTop.get(0).hotelId();
        var topDto = new HabitacionPopularDTO.Top(habitacionTopId, hotelDelTop, top.getValue());

        return new HabitacionPopularDTO(desde, hasta, hotelId, topDto, ranking, reservasTop);
    }
}
