// reviews.microservice/reviewPlatillo/domain/ReviewPlatillo.java
package com.reviews.microservice.reviewPlatillo.domain;

import lombok.Getter;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
public class ReviewPlatillo {
  private final UUID id;
  private final UUID cuentaId;
  private final UUID restauranteId;
  private final UUID platilloId;
  private final UUID clienteId; // puede ser null si no lo tienes aún
  private int estrellas;        // 1..5
  private String comentario;    // opcional
  private boolean enabled;
  private final Instant createdAt;
  private Instant updatedAt;

  public ReviewPlatillo(UUID id, UUID cuentaId, UUID restauranteId, UUID platilloId,
                        UUID clienteId, int estrellas, String comentario,
                        boolean enabled, Instant createdAt, Instant updatedAt) {
    this.id = Objects.requireNonNull(id);
    this.cuentaId = Objects.requireNonNull(cuentaId);
    this.restauranteId = Objects.requireNonNull(restauranteId);
    this.platilloId = Objects.requireNonNull(platilloId);
    this.clienteId = clienteId; // opcional
    setEstrellas(estrellas);
    this.comentario = comentario;
    this.enabled = enabled;
    this.createdAt = createdAt == null ? Instant.now() : createdAt;
    this.updatedAt = updatedAt == null ? this.createdAt : updatedAt;
  }

  public static ReviewPlatillo nueva(UUID cuentaId, UUID restauranteId, UUID platilloId,
                                     UUID clienteId, int estrellas, String comentario) {
    return new ReviewPlatillo(UUID.randomUUID(), cuentaId, restauranteId, platilloId,
        clienteId, estrellas, comentario, true, Instant.now(), Instant.now());
  }

  public void setEstrellas(int v){
    if (v < 1 || v > 5) throw new IllegalArgumentException("Estrellas debe ser 1..5");
    this.estrellas = v;
    this.updatedAt = Instant.now();
  }

  public void deshabilitar(){ this.enabled = false; this.updatedAt = Instant.now(); }
  public void habilitar(){ this.enabled = true; this.updatedAt = Instant.now(); }
}