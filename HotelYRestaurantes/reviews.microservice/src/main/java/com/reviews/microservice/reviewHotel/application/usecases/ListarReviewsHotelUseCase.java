// application/usecases/ListarReviewsHotelUseCase.java
package com.reviews.microservice.reviewHotel.application.usecases;

import com.reviews.microservice.reviewHotel.application.inputports.ListarReviewsHotelInputPort;
import com.reviews.microservice.reviewHotel.application.outputports.persistence.ReviewHotelRepositorioPort;
import com.reviews.microservice.reviewHotel.domain.ReviewHotel;

import java.util.List;
import java.util.UUID;

public class ListarReviewsHotelUseCase implements ListarReviewsHotelInputPort {
  private final ReviewHotelRepositorioPort repo;
  public ListarReviewsHotelUseCase(ReviewHotelRepositorioPort repo){ this.repo = repo; }

  @Override public List<ReviewHotel> porHotel(UUID hotelId){ return repo.listarPorHotel(hotelId); }
  @Override public List<ReviewHotel> porFactura(UUID facturaHotelId){ return repo.listarPorFactura(facturaHotelId); }
}