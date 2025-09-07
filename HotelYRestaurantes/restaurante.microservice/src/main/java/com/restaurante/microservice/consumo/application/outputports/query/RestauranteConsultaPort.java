package com.restaurante.microservice.consumo.application.outputports.query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

// Config necesaria para cálculo de totales:
public interface RestauranteConsultaPort {
  Optional<ConfigRestaurante> obtenerConfig(UUID restauranteId);

  record ConfigRestaurante(BigDecimal impuestoPorc, BigDecimal propinaPorcDefault) {}
}