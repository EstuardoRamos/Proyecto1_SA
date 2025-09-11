// application/usecases/CrearReviewHotelUseCase.java
package com.reviews.microservice.reviewHotel.application.usecases;

import com.reviews.microservice.reviewHotel.application.inputports.CrearReviewHotelInputPort;
import com.reviews.microservice.reviewHotel.application.outputports.persistence.ReviewHotelRepositorioPort;
import com.reviews.microservice.reviewHotel.application.outputports.query.FacturacionQueryPort;
import com.reviews.microservice.reviewHotel.domain.ReviewHotel;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CrearReviewHotelUseCase implements CrearReviewHotelInputPort {

  private final ReviewHotelRepositorioPort repo;
  private final FacturacionQueryPort fact;

  public CrearReviewHotelUseCase(ReviewHotelRepositorioPort repo, FacturacionQueryPort fact) {
    this.repo = repo;
    this.fact = fact;
  }

  @Override
  public ReviewHotel crear(UUID facturaHotelId, int estrellas, String comentario, List<String> tags) {
    var snap = fact.facturaHotel(facturaHotelId)
        .orElseThrow(() -> new IllegalArgumentException("Factura de hotel no encontrada"));

    UUID hotelId = Objects.requireNonNull(snap.hotelId(), "hotelId nulo en factura");
    if (!"EMITIDA".equalsIgnoreCase(snap.estado())) {
      throw new IllegalStateException("Factura no está EMITIDA");
    }
    if (repo.existsByFacturaHotelId(facturaHotelId)) {
      throw new IllegalStateException("La factura ya tiene una review");
    }

    var review = ReviewHotel.nueva(
        facturaHotelId,
        hotelId,
        snap.habitacionId(), // opcional
        snap.clienteId(),    // opcional
        estrellas,
        comentario,
        tags
    );
    return repo.guardar(review);
  }
}