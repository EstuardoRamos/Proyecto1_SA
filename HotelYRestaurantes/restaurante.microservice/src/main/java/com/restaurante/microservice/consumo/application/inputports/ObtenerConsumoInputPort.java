package com.restaurante.microservice.consumo.application.inputports;

import java.util.Optional;
import java.util.UUID;

public interface ObtenerConsumoInputPort {

    Optional<com.restaurante.microservice.consumo.domain.ConsumoItem> porId(UUID id);
}
