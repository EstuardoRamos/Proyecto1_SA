// com/restaurante/microservice/facturacion/infrastructure/inputadapters/rest/dto/FacturaResponse.java
package com.restaurante.microservice.facturacion.infraestructure.inputadapters.rest.dto;

import com.restaurante.microservice.facturacion.infraestructure.inputadapters.rest.dto.FacturaItemResponse;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public record FacturaResponse(
        java.util.UUID id, String serie, Integer numero, java.util.UUID restauranteId, java.util.UUID cuentaId,
        String moneda, BigDecimal subtotal, BigDecimal impuesto, BigDecimal propina, BigDecimal total,
        String estado, Instant createdAt, List<FacturaItemResponse> items
        ) {

    public static FacturaResponse from(com.restaurante.microservice.facturacion.domain.Factura f) {
        var items = f.getItems().stream()
                .map(i -> new FacturaItemResponse(i.getId(), i.getNombre(), i.getPrecioUnitario(), i.getCantidad(), i.getSubtotal()))
                .collect(Collectors.toList());
        return new FacturaResponse(f.getId(), f.getSerie(), f.getNumero(), f.getRestauranteId(), f.getCuentaId(),
                f.getMoneda(), f.getSubtotal(), f.getImpuesto(), f.getPropina(), f.getTotal(),
                f.getEstado().name(), f.getCreatedAt(), items);
    }
}
