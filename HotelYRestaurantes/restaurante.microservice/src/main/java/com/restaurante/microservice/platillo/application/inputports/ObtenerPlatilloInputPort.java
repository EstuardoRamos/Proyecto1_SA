package com.restaurante.microservice.platillo.application.inputports;

import com.restaurante.microservice.platillo.domain.Platillo;

import java.util.UUID;



public interface ObtenerPlatilloInputPort {
  Platillo obtener(UUID id);
}

