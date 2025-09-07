package com.restaurante.microservice.platillo.application.outputports.persistence;

import com.restaurante.microservice.platillo.domain.Platillo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PlatilloRepositorioOutputPort {

    Platillo save(Platillo p);

    Optional<Platillo> findById(UUID id);

    boolean existsByNombreInRestaurante(UUID restauranteId, String nombre);

    Page<Platillo> list(String q, UUID restauranteId, Boolean enabled, Pageable pageable);
}
