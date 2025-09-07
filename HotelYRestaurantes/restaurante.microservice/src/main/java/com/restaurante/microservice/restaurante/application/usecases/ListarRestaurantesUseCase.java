package com.restaurante.microservice.restaurante.application.usecases;

import com.restaurante.microservice.restaurante.application.inputoports.ListarRestaurantesInputPort;
import com.restaurante.microservice.restaurante.application.outputports.RestauranteRepositorioPort;
import com.restaurante.microservice.restaurante.domain.Restaurante;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListarRestaurantesUseCase implements ListarRestaurantesInputPort {
  private final RestauranteRepositorioPort repo;
  public ListarRestaurantesUseCase(RestauranteRepositorioPort repo){ this.repo = repo; }
  @Override public Page<Restaurante> listar(String q, UUID hotelId, Boolean enabled, Pageable p) {
    return repo.buscar(q, hotelId, enabled, (org.springframework.data.domain.Pageable) p);
  }
}