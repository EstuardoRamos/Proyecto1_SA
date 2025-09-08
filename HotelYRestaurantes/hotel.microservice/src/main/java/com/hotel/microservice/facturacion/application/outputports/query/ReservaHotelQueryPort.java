package com.hotel.microservice.facturacion.application.outputports.query;

import java.math.BigDecimal;
import java.util.*;

public interface ReservaHotelQueryPort {

    Optional<ReservaFacturaSnapshot> snapshotFactura(java.util.UUID reservaId);

    record Item(String descripcion, BigDecimal precioUnitario, int cantidad, BigDecimal subtotal) {

    }

    record ReservaFacturaSnapshot(
            java.util.UUID hotelId,
            java.util.UUID reservaId,
            String moneda, // e.g. "GTQ"
            BigDecimal subtotal,
            BigDecimal impuesto,
            BigDecimal propina,
            BigDecimal total,
            List<Item> items,
            String estadoReserva // p.ej. "CHECKOUT" / "FINALIZADA"
            ) {

    }
}
