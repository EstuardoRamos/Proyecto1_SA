package com.restaurante.microservice.consumo.application.inputports;

import com.restaurante.microservice.consumo.domain.ConsumoItem;
import java.util.UUID;

public interface ActualizarConsumoInputPort {
  ConsumoItem actualizar(UUID id, java.math.BigDecimal precioUnitario, Integer cantidad, String nota);
}
