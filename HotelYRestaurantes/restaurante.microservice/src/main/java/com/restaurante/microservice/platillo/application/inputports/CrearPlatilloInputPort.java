package com.restaurante.microservice.platillo.application.inputports;

import com.restaurante.microservice.platillo.domain.Platillo;

import java.math.BigDecimal;
import java.util.UUID;

public interface CrearPlatilloInputPort {
  Platillo crear(UUID restauranteId, String nombre, String descripcion,
                 BigDecimal precio, String imagenUrl, Boolean disponible);
}

