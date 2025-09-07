package com.restaurante.microservice.platillo.application.usecases;

import com.restaurante.microservice.common.errors.AlreadyExistsException;
import com.restaurante.microservice.platillo.application.inputports.CrearPlatilloInputPort;
import com.restaurante.microservice.platillo.application.outputports.persistence.PlatilloRepositorioOutputPort;
import com.restaurante.microservice.platillo.domain.Platillo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CrearPlatilloUseCase implements CrearPlatilloInputPort {

  private final PlatilloRepositorioOutputPort repo;

  @Override
  public Platillo crear(UUID restauranteId, String nombre, String descripcion,
                        BigDecimal precio, String imagenUrl, Boolean disponible) {
    if (restauranteId != null && repo.existsByNombreInRestaurante(restauranteId, nombre))
      throw new AlreadyExistsException("Ya existe un platillo con ese nombre en el restaurante");

    var now = Instant.now();
    var p = Platillo.builder()
        .id(UUID.randomUUID())
        .restauranteId(restauranteId)
        .nombre(nombre)
        .descripcion(descripcion)
        .precio(precio)
        .imagenUrl(imagenUrl)
        .disponible(disponible != null ? disponible : true)
        .enabled(true)
        .createdAt(now)
        .updatedAt(now)
        .build();

    return repo.save(p);
  }
}