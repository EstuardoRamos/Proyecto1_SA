package com.restaurante.microservice.restaurante.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Restaurante {
  private final UUID id;
  private UUID hotelId;                   // puede ser null
  private String nombre;
  private String direccion;
  private BigDecimal impuestoPorc;       // 0..1 (ej. 0.12)
  private BigDecimal propinaPorcDefault; // 0..1 (ej. 0.10)
  private boolean enabled;
  private final Instant createdAt;
  private Instant updatedAt;

  public Restaurante(UUID id, UUID hotelId, String nombre, String direccion,
                      BigDecimal impuestoPorc, BigDecimal propinaPorcDefault,
                      boolean enabled, Instant createdAt, Instant updatedAt) {
    this.id = Objects.requireNonNull(id);
    setNombre(nombre);
    this.hotelId = hotelId;
    this.direccion = direccion;
    setImpuestoPorc(impuestoPorc);
    setPropinaPorcDefault(propinaPorcDefault);
    this.enabled = enabled;
    this.createdAt = createdAt == null ? Instant.now() : createdAt;
    this.updatedAt = updatedAt == null ? this.createdAt : updatedAt;
  }

  public static Restaurante crear(UUID hotelId, String nombre, String direccion,
                                  BigDecimal impuestoPorc, BigDecimal propinaPorcDefault) {
    return new Restaurante(UUID.randomUUID(), hotelId, nombre, direccion,
        impuestoPorc, propinaPorcDefault, true, Instant.now(), Instant.now());
  }
  
  public static Restaurante rehidratar(UUID id, UUID hotelId, String nombre, String direccion,
                                     BigDecimal impuestoPorc, BigDecimal propinaPorcDefault,
                                     boolean enabled, Instant createdAt, Instant updatedAt) {
  return new Restaurante(id, hotelId, nombre, direccion,
      impuestoPorc, propinaPorcDefault, enabled, createdAt, updatedAt);
}

  public void actualizar(String nombre, String direccion,
                         BigDecimal impuestoPorc, BigDecimal propinaPorcDefault) {
    setNombre(nombre);
    this.direccion = direccion;
    setImpuestoPorc(impuestoPorc);
    setPropinaPorcDefault(propinaPorcDefault);
    touch();
  }

  public void vincularHotel(UUID hotelId) { this.hotelId = hotelId; touch(); }
  public void desvincularHotel() { this.hotelId = null; touch(); }
  public void deshabilitar() { this.enabled = false; touch(); }
  public void habilitar() { this.enabled = true; touch(); }

  private void setNombre(String nombre) {
    if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre requerido");
    this.nombre = nombre.trim();
  }
  private void setImpuestoPorc(BigDecimal v) {
    if (v == null || v.compareTo(BigDecimal.ZERO) < 0 || v.compareTo(BigDecimal.ONE) > 0)
      throw new IllegalArgumentException("Impuesto fuera de rango 0..1");
    this.impuestoPorc = v;
  }
  private void setPropinaPorcDefault(BigDecimal v) {
    if (v == null || v.compareTo(BigDecimal.ZERO) < 0 || v.compareTo(BigDecimal.ONE) > 0)
      throw new IllegalArgumentException("Propina por defecto fuera de rango 0..1");
    this.propinaPorcDefault = v;
  }
  private void touch(){ this.updatedAt = Instant.now(); }

  public UUID getId(){ return id; }
  public UUID getHotelId(){ return hotelId; }
  public String getNombre(){ return nombre; }
  public String getDireccion(){ return direccion; }
  public BigDecimal getImpuestoPorc(){ return impuestoPorc; }
  public BigDecimal getPropinaPorcDefault(){ return propinaPorcDefault; }
  public boolean isEnabled(){ return enabled; }
  public Instant getCreatedAt(){ return createdAt; }
  public Instant getUpdatedAt(){ return updatedAt; }
}