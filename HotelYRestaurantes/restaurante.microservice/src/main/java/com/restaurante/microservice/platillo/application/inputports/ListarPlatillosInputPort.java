package com.restaurante.microservice.platillo.application.inputports;

import com.restaurante.microservice.platillo.domain.Platillo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ListarPlatillosInputPort {
  Page<Platillo> listar(String q, UUID restauranteId, Boolean enabled, Pageable pageable);
}