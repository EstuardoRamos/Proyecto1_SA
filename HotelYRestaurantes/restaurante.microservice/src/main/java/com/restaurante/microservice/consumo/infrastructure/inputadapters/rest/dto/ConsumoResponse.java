package com.restaurante.microservice.consumo.infrastructure.inputadapters.rest.dto;

import com.restaurante.microservice.consumo.domain.ConsumoItem;
import java.math.BigDecimal;
import java.util.UUID;

public record ConsumoResponse(UUID id, UUID cuentaId, UUID platilloId, String nombre,
        BigDecimal precioUnitario, int cantidad, BigDecimal subtotal, String nota) {

    public static ConsumoResponse from(ConsumoItem i) {
        return new ConsumoResponse(i.getId(), i.getCuentaId(), i.getPlatilloId(), i.getNombre(),
                i.getPrecioUnitario(), i.getCantidad(), i.getSubtotal(), i.getNota());
    }
}
