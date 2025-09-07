package com.hotel.microservice.habitacion.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Habitacion {

    private UUID id;
    private UUID hotelId;
    private String numero;       // único por hotel
    private String tipo;         // ej: STANDARD, SUITE, etc. (puede ser enum luego)
    private int capacidad;
    private BigDecimal precioBase;
    private Estado estado;       // DISPONIBLE, MANTENIMIENTO
    private String descripcion;

    public enum Estado {
        DISPONIBLE, MANTENIMIENTO
    }

    public Habitacion(UUID id, UUID hotelId, String numero, String tipo,
            int capacidad, BigDecimal precioBase, Estado estado, String descripcion) {
        this.id = Objects.requireNonNull(id);
        this.hotelId = Objects.requireNonNull(hotelId);
        setNumero(numero);
        setTipo(tipo);
        setCapacidad(capacidad);
        setPrecioBase(precioBase);
        this.estado = estado == null ? Estado.DISPONIBLE : estado;
        this.descripcion = descripcion;
    }

    public static Habitacion nueva(UUID hotelId, String numero, String tipo,
            int capacidad, BigDecimal precioBase, String descripcion) {
        return new Habitacion(UUID.randomUUID(), hotelId, numero, tipo, capacidad, precioBase, Estado.DISPONIBLE, descripcion);
    }

    // invariantes
    public void setNumero(String n) {
        if (n == null || n.isBlank()) {
            throw new IllegalArgumentException("El número es obligatorio");
        }
        this.numero = n.trim();
    }

    public void setTipo(String t) {
        if (t == null || t.isBlank()) {
            throw new IllegalArgumentException("El tipo es obligatorio");
        }
        this.tipo = t.trim();
    }

    public void setCapacidad(int c) {
        if (c < 1) {
            throw new IllegalArgumentException("La capacidad debe ser >= 1");
        }
        this.capacidad = c;
    }

    public void setPrecioBase(BigDecimal p) {
        if (p == null || p.signum() <= 0) {
            throw new IllegalArgumentException("El precioBase debe ser > 0");
        }
        this.precioBase = p;
    }

    public void cambiarEstado(Estado e) {
        this.estado = Objects.requireNonNull(e);
    }

    
    
}
