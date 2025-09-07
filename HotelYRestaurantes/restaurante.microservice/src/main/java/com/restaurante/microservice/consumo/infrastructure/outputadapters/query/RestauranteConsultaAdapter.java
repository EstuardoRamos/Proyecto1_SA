// infrastrucuture/outputadapters/query/RestauranteConsultaAdapter.java
package com.restaurante.microservice.consumo.infrastructure.outputadapters.query;

import com.restaurante.microservice.consumo.application.outputports.query.RestauranteConsultaPort;
import com.restaurante.microservice.restaurante.infrastrucuture.outputadapters.persistence.respository.RestauranteJpaRepository;

import java.util.Optional;
import java.util.UUID;

public class RestauranteConsultaAdapter implements RestauranteConsultaPort {

  private final RestauranteJpaRepository restaurantes;

  public RestauranteConsultaAdapter(RestauranteJpaRepository restaurantes){ this.restaurantes=restaurantes; }

  @Override
  public Optional<ConfigRestaurante> obtenerConfig(UUID restauranteId) {
    return restaurantes.findById(restauranteId).map(e ->
        new ConfigRestaurante(e.getImpuestoPorc(), e.getPropinaPorcDefault()));
  }
}