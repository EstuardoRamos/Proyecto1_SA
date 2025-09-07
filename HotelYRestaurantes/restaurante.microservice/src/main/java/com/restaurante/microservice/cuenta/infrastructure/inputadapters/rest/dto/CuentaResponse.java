package com.restaurante.microservice.cuenta.infrastructure.inputadapters.rest.dto;

import com.restaurante.microservice.cuenta.domain.Cuenta;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Data                   // getters/setters + equals/hashCode + toString
@NoArgsConstructor      // ctor vacío (muchas libs lo piden)
@AllArgsConstructor
@Builder
public class CuentaResponse {
    private UUID id;
    private UUID restauranteId;
    private String mesa;
    private String estado;
    private BigDecimal subtotal;
    private BigDecimal impuesto;
    private BigDecimal propina;
    private BigDecimal total;

    public static CuentaResponse from(Cuenta c) {
        return CuentaResponse.builder()
                .id(c.getId())
                .restauranteId(c.getRestauranteId())
                .mesa(c.getMesa())
                .estado(c.getEstado().name())
                .subtotal(c.getSubtotal())
                .impuesto(c.getImpuesto())
                .propina(c.getPropina())
                .total(c.getTotal())
                .build();
    }
}
