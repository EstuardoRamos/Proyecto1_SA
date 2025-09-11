// domain/ReviewHotel.java
package com.reviews.microservice.reviewHotel.domain;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ReviewHotel {
  private final UUID id;
  private final UUID facturaHotelId;
  private final UUID hotelId;
  private final UUID habitacionId; // puede ser null
  private final UUID clienteId;    // puede ser null
  private int estrellas;           // 1..5
  private String comentario;
  private List<String> tags;
  private boolean enabled;
  private final Instant createdAt;
  private Instant updatedAt;

  public ReviewHotel(UUID id, UUID facturaHotelId, UUID hotelId,
                     UUID habitacionId, UUID clienteId,
                     int estrellas, String comentario, List<String> tags,
                     boolean enabled, Instant createdAt, Instant updatedAt) {
    this.id = Objects.requireNonNull(id);
    this.facturaHotelId = Objects.requireNonNull(facturaHotelId);
    this.hotelId = Objects.requireNonNull(hotelId);
    this.habitacionId = habitacionId; // opcional
    this.clienteId = clienteId;       // opcional
    setEstrellas(estrellas);
    this.comentario = comentario;
    this.tags = (tags == null ? List.of() : List.copyOf(tags));
    this.enabled = enabled;
    this.createdAt = (createdAt == null ? Instant.now() : createdAt);
    this.updatedAt = (updatedAt == null ? this.createdAt : updatedAt);
  }

  public static ReviewHotel nueva(UUID facturaHotelId, UUID hotelId,
                                 UUID habitacionId, UUID clienteId,
                                 int estrellas, String comentario, List<String> tags) {
    return new ReviewHotel(UUID.randomUUID(), facturaHotelId, hotelId,
        habitacionId, clienteId, estrellas, comentario, tags,
        true, Instant.now(), Instant.now());
  }

  public void setEstrellas(int v){
    if (v < 1 || v > 5) throw new IllegalArgumentException("estrellas debe ser 1..5");
    this.estrellas = v;
    this.updatedAt = Instant.now();
  }
}