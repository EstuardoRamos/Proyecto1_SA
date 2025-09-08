package com.hotel.microservice.reserva.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter

public class Reserva {

    public enum Estado {
        RESERVADA, CHECKED_IN, CHECKED_OUT, CANCELADA
    }

    private final UUID id;
    private final UUID hotelId;
    private final UUID habitacionId;
    private final UUID clienteId;      // viene de auth
    private LocalDate entrada;         // inclusive
    private LocalDate salida;          // exclusive (típico)
    private int huespedes;
    private Estado estado;
    private BigDecimal total;

    public Reserva(UUID id, UUID hotelId, UUID habitacionId, UUID clienteId,
            LocalDate entrada, LocalDate salida, int huespedes,
            Estado estado, BigDecimal total) {
        this.id = Objects.requireNonNull(id);
        this.hotelId = Objects.requireNonNull(hotelId);
        this.habitacionId = Objects.requireNonNull(habitacionId);
        this.clienteId = Objects.requireNonNull(clienteId);
        setRango(entrada, salida);
        setHuespedes(huespedes);
        this.estado = estado == null ? Estado.RESERVADA : estado;
        this.total = total == null ? BigDecimal.ZERO : total;
    }

    public static Reserva nueva(UUID hotelId, UUID habitacionId, UUID clienteId,
            LocalDate entrada, LocalDate salida, int huespedes) {
        return new Reserva(UUID.randomUUID(), hotelId, habitacionId, clienteId,
                entrada, salida, huespedes, Estado.RESERVADA, BigDecimal.ZERO);
    }

    public void setRango(LocalDate in, LocalDate out) {
        if (in == null || out == null || !out.isAfter(in)) {
            throw new IllegalArgumentException("La salida debe ser posterior a la entrada");
        }
        this.entrada = in;
        this.salida = out;
    }

    public void setHuespedes(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Huéspedes debe ser >= 1");
        }
        this.huespedes = n;
    }

    public long noches() {
        return ChronoUnit.DAYS.between(entrada, salida);
    }

    public void setTotal(BigDecimal t) {
        this.total = t;
    }

    public void checkIn() {
        if (estado != Estado.RESERVADA) {
            throw new IllegalStateException("No se puede hacer check-in");
        }
        estado = Estado.CHECKED_IN;
    }

    public void checkOut() {
        if (estado != Estado.CHECKED_IN) {
            throw new IllegalStateException("No se puede hacer check-out");
        }
        estado = Estado.CHECKED_OUT;
    }

    public void cancelar() {
        if (estado == Estado.CHECKED_OUT) {
            throw new IllegalStateException("No se puede cancelar");
        }
        estado = Estado.CANCELADA;
    }

    public UUID getId() {
        return id;
    }

    public UUID getHotelId() {
        return hotelId;
    }

    public UUID getHabitacionId() {
        return habitacionId;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public LocalDate getEntrada() {
        return entrada;
    }

    public LocalDate getSalida() {
        return salida;
    }

    public int getHuespedes() {
        return huespedes;
    }

    public Estado getEstado() {
        return estado;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
