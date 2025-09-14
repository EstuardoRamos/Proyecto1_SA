// application/usecases/CalcularRestauranteMasPopularUseCase.java
package com.reportes.microservice.popularidad.application.usecases;

import com.reportes.microservice.popularidad.application.dto.RestaurantePopularDTO;
import com.reportes.microservice.popularidad.application.inputports.CalcularRestauranteMasPopularPort;
import com.reportes.microservice.popularidad.application.outputports.FacturasRestQueryPort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class CalcularRestauranteMasPopularUseCase implements CalcularRestauranteMasPopularPort {

    private final FacturasRestQueryPort facturas;

    public CalcularRestauranteMasPopularUseCase(FacturasRestQueryPort facturas) {
        this.facturas = facturas;
    }

    @Override
    public RestaurantePopularDTO ejecutar(LocalDate desde, LocalDate hasta, UUID restauranteId) {
        // Llamada al port con firma: (desde, hasta, restauranteId)
        var snaps = facturas.listar(restauranteId, desde, hasta ).stream()
                .filter(f -> "EMITIDA".equalsIgnoreCase(f.estado()))
                .toList();

        if (snaps.isEmpty()) {
            return new RestaurantePopularDTO(desde, hasta, restauranteId, null, List.of(), List.of());
        }

        // Total de ingresos por restaurante
        Map<UUID, BigDecimal> ingresos = snaps.stream().collect(Collectors.groupingBy(
                FacturasRestQueryPort.FacturaSnap::restauranteId,
                Collectors.reducing(BigDecimal.ZERO,
                        FacturasRestQueryPort.FacturaSnap::total, BigDecimal::add)
        ));

        // Si se pide un restaurante específico, usamos ese; si no, tomamos el top por ingresos
        Map.Entry<UUID, BigDecimal> topEntry = (restauranteId != null)
                ? Map.entry(restauranteId, ingresos.getOrDefault(restauranteId, BigDecimal.ZERO))
                : ingresos.entrySet().stream()
                          .max(Map.Entry.comparingByValue())
                          .orElseThrow();

        // Facturas del restaurante top (o del solicitado)
        var factTop = snaps.stream()
                .filter(f -> Objects.equals(f.restauranteId(), topEntry.getKey()))
                .map(f -> new RestaurantePopularDTO.Factura(
                        f.id(), f.restauranteId(), f.cuentaId(), f.clienteId(),
                        f.createdAt(), f.subtotal(), f.impuesto(), f.propina(), f.total(), f.estado()))
                .toList();

        // Ranking descendente por ingresos
        var ranking = ingresos.entrySet().stream()
                .sorted(Map.Entry.<UUID, BigDecimal>comparingByValue().reversed())
                .map(e -> new RestaurantePopularDTO.ItemRanking(e.getKey(), e.getValue()))
                .toList();

        var top = new RestaurantePopularDTO.Top(topEntry.getKey(), topEntry.getValue(), factTop.size());

        return new RestaurantePopularDTO(desde, hasta, restauranteId, top, ranking, factTop);
    }
}