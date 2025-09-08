package com.hotel.microservice.facturacion.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import lombok.Getter;

@Getter
public class FacturaHotel {

    private final UUID id;
    private final UUID hotelId;
    private final UUID reservaId;
    private final String moneda;
    private final String serie;
    private final Integer numero;
    private final String clienteNit;
    private final String clienteNombre;
    private final BigDecimal subtotal;
    private final BigDecimal impuesto;
    private final BigDecimal propina;   // opcional (p.ej. room service)
    private final BigDecimal total;
    private final Instant createdAt;
    private final EstadoFacturaHotel estado;
    private final List<FacturaHotelItem> items;

    private FacturaHotel(UUID id, UUID hotelId, UUID reservaId, String moneda, String serie, Integer numero,
            String nit, String cliente, BigDecimal sub, BigDecimal imp, BigDecimal prop,
            BigDecimal tot, Instant createdAt, EstadoFacturaHotel estado, List<FacturaHotelItem> items) {
        this.id = id;
        this.hotelId = hotelId;
        this.reservaId = reservaId;
        this.moneda = moneda;
        this.serie = serie;
        this.numero = numero;
        this.clienteNit = nit;
        this.clienteNombre = cliente;
        this.subtotal = sub;
        this.impuesto = imp;
        this.propina = prop;
        this.total = tot;
        this.createdAt = createdAt;
        this.estado = estado;
        this.items = List.copyOf(items);
    }

    public static FacturaHotel emitir(UUID hotelId, UUID reservaId, String moneda, String serie, Integer numero,
            String nit, String cliente, BigDecimal sub, BigDecimal imp, BigDecimal prop,
            BigDecimal tot, List<FacturaHotelItem> items) {
        return new FacturaHotel(UUID.randomUUID(), hotelId, reservaId, moneda, serie, numero,
                (nit == null || nit.isBlank() ? "CF" : nit),
                (cliente == null || cliente.isBlank() ? "Consumidor Final" : cliente),
                nz(sub), nz(imp), nz(prop), nz(tot),
                Instant.now(), EstadoFacturaHotel.EMITIDA, new ArrayList<>(items));
    }

    public static FacturaHotel rehidratar(UUID id, UUID hotelId, UUID reservaId, String moneda, String serie, Integer numero,
            String nit, String cliente, BigDecimal sub, BigDecimal imp, BigDecimal prop,
            BigDecimal tot, Instant createdAt, EstadoFacturaHotel estado, List<FacturaHotelItem> items) {
        return new FacturaHotel(id, hotelId, reservaId, moneda, serie, numero, nit, cliente, sub, imp, prop, tot, createdAt, estado, items);
    }

    public FacturaHotel anular() {
        return new FacturaHotel(this.id, this.hotelId, this.reservaId, this.moneda, this.serie, this.numero,
                this.clienteNit, this.clienteNombre, this.subtotal, this.impuesto, this.propina, this.total,
                this.createdAt, EstadoFacturaHotel.ANULADA, this.items);
    }

    private static BigDecimal nz(BigDecimal v) {
        return v == null ? java.math.BigDecimal.ZERO : v;
    }
}
