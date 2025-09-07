package com.restaurante.microservice.consumo.application.usecases;

import com.restaurante.microservice.consumo.application.inputports.ObtenerConsumoInputPort;
import com.restaurante.microservice.consumo.application.outputports.persistence.ConsumoRepositorioPort;
import com.restaurante.microservice.consumo.domain.ConsumoItem;

import java.util.Optional;
import java.util.UUID;

public class ObtenerConsumoUseCase implements ObtenerConsumoInputPort {

  private final ConsumoRepositorioPort consumos;

  public ObtenerConsumoUseCase(ConsumoRepositorioPort consumos) {
    this.consumos = consumos;
  }

  @Override
  public Optional<ConsumoItem> porId(UUID id) {
    return consumos.porId(id);
  }
}