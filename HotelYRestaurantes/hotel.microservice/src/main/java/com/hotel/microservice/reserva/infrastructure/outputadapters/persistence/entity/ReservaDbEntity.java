package com.hotel.microservice.reserva.infrastructure.outputadapters.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class ReservaDbEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36)
    private UUID id;
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "hotel_id", length = 36, nullable = false)
    private UUID hotelId;
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "room_id", length = 36, nullable = false)
    private UUID habitacionId;
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "client_id", length = 36, nullable = false)
    private UUID clienteId;
    @Column(nullable = false)
    private LocalDate entrada;
    @Column(nullable = false)
    private LocalDate salida;
    @Column(nullable = false)
    private int huespedes;
    @Column(nullable = false, length = 20)
    private String estado;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    // getters/setters...
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelId(UUID v) {
        hotelId = v;
    }

    public UUID getHabitacionId() {
        return habitacionId;
    }

    public void setHabitacionId(UUID v) {
        habitacionId = v;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID v) {
        clienteId = v;
    }

    public LocalDate getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDate v) {
        entrada = v;
    }

    public LocalDate getSalida() {
        return salida;
    }

    public void setSalida(LocalDate v) {
        salida = v;
    }

    public int getHuespedes() {
        return huespedes;
    }

    public void setHuespedes(int v) {
        huespedes = v;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String v) {
        estado = v;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal v) {
        total = v;
    }
}
