package com.reportes.microservice.popularidad.application.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RestaurantePopularDTO(
        LocalDate desde, LocalDate hasta, UUID restauranteIdFiltro,
        Top top, List<ItemRanking> ranking, List<Factura> facturas
        ) {

    public record Top(UUID restauranteId, BigDecimal ingresos, long facturas) {

    }

    public record ItemRanking(UUID restauranteId, BigDecimal ingresos) {

    }

    public record Factura(UUID id, UUID restauranteId, UUID cuentaId, UUID clienteId,
            Instant createdAt, BigDecimal subtotal, BigDecimal impuesto,
            BigDecimal propina, BigDecimal total, String estado) {

    }
}
