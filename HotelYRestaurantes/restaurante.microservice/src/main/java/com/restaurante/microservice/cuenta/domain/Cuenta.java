package com.restaurante.microservice.cuenta.domain;

import lombok.Getter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Cuenta {
  private final UUID id;
  private final UUID restauranteId;
  private final String mesa; // puede ser null
  private EstadoCuenta estado;
  private BigDecimal subtotal;
  private BigDecimal impuesto;
  private BigDecimal propina;
  private BigDecimal total;
  private final Instant createdAt;
  private Instant updatedAt;

  private Cuenta(UUID id, UUID restauranteId, String mesa,
                 EstadoCuenta estado,
                 BigDecimal subtotal, BigDecimal impuesto, BigDecimal propina, BigDecimal total,
                 Instant createdAt, Instant updatedAt) {
    this.id = id;
    this.restauranteId = restauranteId;
    this.mesa = mesa;
    this.estado = estado;
    this.subtotal = subtotal;
    this.impuesto = impuesto;
    this.propina = propina;
    this.total = total;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static Cuenta abrir(UUID restauranteId, String mesa) {
    var now = Instant.now();
    return new Cuenta(UUID.randomUUID(), restauranteId, mesa, EstadoCuenta.ABIERTA,
        bd0(), bd0(), bd0(), bd0(), now, now);
  }

  public static Cuenta rehidratar(UUID id, UUID restauranteId, String mesa, EstadoCuenta estado,
                                  BigDecimal subtotal, BigDecimal impuesto, BigDecimal propina, BigDecimal total,
                                  Instant createdAt, Instant updatedAt) {
    return new Cuenta(id, restauranteId, mesa, estado,
        nz(subtotal), nz(impuesto), nz(propina), nz(total),
        createdAt, updatedAt);
  }

  public void cerrar() {
    if (estado != EstadoCuenta.ABIERTA) throw new IllegalStateException("La cuenta no está ABIERTA");
    this.estado = EstadoCuenta.CERRADA;
    this.updatedAt = Instant.now();
  }

  public void cobrar() {
    if (estado != EstadoCuenta.CERRADA) throw new IllegalStateException("La cuenta debe estar CERRADA");
    this.estado = EstadoCuenta.COBRADA;
    this.updatedAt = Instant.now();
  }

  public void setTotales(BigDecimal subtotal, BigDecimal impuesto, BigDecimal propina) {
    this.subtotal = nz(subtotal);
    this.impuesto = nz(impuesto);
    this.propina = nz(propina);
    this.total = this.subtotal.add(this.impuesto).add(this.propina);
    this.updatedAt = Instant.now();
  }

  private static BigDecimal bd0() { return BigDecimal.ZERO; }
  private static BigDecimal nz(BigDecimal v) { return v == null ? bd0() : v; }
}