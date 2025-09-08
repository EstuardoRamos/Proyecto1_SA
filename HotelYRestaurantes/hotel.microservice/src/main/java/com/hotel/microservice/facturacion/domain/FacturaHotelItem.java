package com.hotel.microservice.facturacion.domain;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class FacturaHotelItem {

    private final UUID id;
    private final String descripcion;       // p.ej. "Noche Hab. 304", "Late checkout"
    private final BigDecimal precioUnitario;
    private final int cantidad;
    private final BigDecimal subtotal;

    private FacturaHotelItem(UUID id, String desc, BigDecimal pu, int qty, BigDecimal sub) {
        this.id = id;
        this.descripcion = desc;
        this.precioUnitario = pu;
        this.cantidad = qty;
        this.subtotal = sub;
    }

    public static FacturaHotelItem rehidratar(UUID id, String desc, BigDecimal pu, int qty, BigDecimal sub) {
        return new FacturaHotelItem(id, desc, pu, qty, sub);
    }
}
