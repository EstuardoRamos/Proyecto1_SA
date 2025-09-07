// com/restaurante/microservice/facturacion/domain/FacturaItem.java
package com.restaurante.microservice.facturacion.domain;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class FacturaItem {

    private final UUID id;
    private final String nombre;
    private final BigDecimal precioUnitario;
    private final int cantidad;
    private final BigDecimal subtotal;

    private FacturaItem(UUID id, String nombre, BigDecimal precioUnitario, int cantidad, BigDecimal subtotal) {
        this.id = id;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public static FacturaItem crear(String nombre, BigDecimal precioUnitario, int cantidad) {
        var sub = (precioUnitario == null ? BigDecimal.ZERO : precioUnitario).multiply(BigDecimal.valueOf(cantidad));
        return new FacturaItem(UUID.randomUUID(), nombre, precioUnitario, cantidad, sub);
    }

    public static FacturaItem rehidratar(UUID id, String nombre, BigDecimal precioUnitario, int cantidad, BigDecimal subtotal) {
        return new FacturaItem(id, nombre, precioUnitario, cantidad, subtotal);
    }
}
