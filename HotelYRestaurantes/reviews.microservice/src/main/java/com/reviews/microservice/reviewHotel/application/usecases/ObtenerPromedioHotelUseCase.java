// application/usecases/ObtenerPromedioHotelUseCase.java
package com.reviews.microservice.reviewHotel.application.usecases;

import com.reviews.microservice.reviewHotel.application.inputports.ObtenerPromedioHotelInputPort;
import com.reviews.microservice.reviewHotel.application.outputports.persistence.ReviewHotelRepositorioPort;

import java.util.UUID;

public class ObtenerPromedioHotelUseCase implements ObtenerPromedioHotelInputPort {
  private final ReviewHotelRepositorioPort repo;
  public ObtenerPromedioHotelUseCase(ReviewHotelRepositorioPort repo){ this.repo = repo; }

  @Override
  public Resultado promedio(UUID hotelId) {
    return new Resultado(hotelId, repo.promedioPorHotel(hotelId), repo.totalPorHotel(hotelId));
  }
}