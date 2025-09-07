package com.restaurante.microservice.restaurante.application.outputports;

import com.restaurante.microservice.restaurante.domain.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface RestauranteRepositorioPort {
  Restaurante guardar(Restaurante r);
  Optional<Restaurante> porId(UUID id);
  Page<Restaurante> buscar(String q, UUID hotelId, Boolean enabled, Pageable pageable);

  // unicidad: nombre por hotel (hotelId puede ser null)
  boolean existsNombreInHotel(String nombre, UUID hotelId, UUID excludeId);
}