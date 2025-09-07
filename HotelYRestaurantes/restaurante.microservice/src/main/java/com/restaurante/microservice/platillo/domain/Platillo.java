package com.restaurante.microservice.platillo.domain;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Platillo {
  private UUID id;
  private UUID restauranteId;          // puede ser null si aún no lo asignas
  private String nombre;
  private String descripcion;          // opcional
  private BigDecimal precio;
  private String imagenUrl;            // opcional
  private boolean disponible;          // para menú
  private boolean enabled;             // borrado lógico
  private Instant createdAt;
  private Instant updatedAt;

  public void actualizar(String nombre, String descripcion, BigDecimal precio, String imagenUrl, Boolean disponible) {
    if (nombre != null) this.nombre = nombre;
    if (descripcion != null) this.descripcion = descripcion;
    if (precio != null) this.precio = precio;
    if (imagenUrl != null) this.imagenUrl = imagenUrl;
    if (disponible != null) this.disponible = disponible;
    this.updatedAt = Instant.now();
  }

  public void deshabilitar() { this.enabled = false; this.updatedAt = Instant.now(); }
}