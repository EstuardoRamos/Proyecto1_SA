package com.hotel.microservice.facturacion.infrastructure.inputadapters.rest.dto;

import com.hotel.microservice.facturacion.domain.FacturaHotel;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public record FacturaHotelResponse(
        java.util.UUID id, String serie, Integer numero,
        java.util.UUID hotelId, java.util.UUID reservaId,
        String moneda, BigDecimal subtotal, BigDecimal impuesto, BigDecimal propina, BigDecimal total,
        String estado, Instant createdAt, java.util.List<FacturaHotelItemResponse> items
        ) {

    public static FacturaHotelResponse from(FacturaHotel f) {
        var items = f.getItems().stream()
                .map(i -> new FacturaHotelItemResponse(i.getId(), i.getDescripcion(), i.getPrecioUnitario(), i.getCantidad(), i.getSubtotal()))
                .collect(Collectors.toList());
        return new FacturaHotelResponse(f.getId(), f.getSerie(), f.getNumero(), f.getHotelId(), f.getReservaId(),
                f.getMoneda(), f.getSubtotal(), f.getImpuesto(), f.getPropina(), f.getTotal(),
                f.getEstado().name(), f.getCreatedAt(), items);
    }
}
