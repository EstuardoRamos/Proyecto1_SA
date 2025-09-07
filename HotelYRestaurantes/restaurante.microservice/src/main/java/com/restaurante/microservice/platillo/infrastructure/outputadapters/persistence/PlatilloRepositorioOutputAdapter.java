package com.restaurante.microservice.platillo.infrastructure.outputadapters.persistence;

import com.restaurante.microservice.platillo.application.outputports.persistence.PlatilloRepositorioOutputPort;
import com.restaurante.microservice.platillo.domain.Platillo;
import com.restaurante.microservice.platillo.infrastructure.outputadapters.persistence.entity.PlatilloDbEntity;
import com.restaurante.microservice.platillo.infrastructure.outputadapters.persistence.repository.PlatilloJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PlatilloRepositorioOutputAdapter implements PlatilloRepositorioOutputPort {

  private final PlatilloJpaRepository jpa;

  private static Platillo toDomain(PlatilloDbEntity e){
    return Platillo.builder()
        .id(e.getId())
        .restauranteId(e.getRestauranteId())
        .nombre(e.getNombre())
        .descripcion(e.getDescripcion())
        .precio(e.getPrecio())
        .imagenUrl(e.getImagenUrl())
        .disponible(e.isDisponible())
        .enabled(e.isEnabled())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt())
        .build();
  }

  private static PlatilloDbEntity toEntity(Platillo d){
    return PlatilloDbEntity.builder()
        .id(d.getId())
        .restauranteId(d.getRestauranteId())
        .nombre(d.getNombre())
        .descripcion(d.getDescripcion())
        .precio(d.getPrecio())
        .imagenUrl(d.getImagenUrl())
        .disponible(d.isDisponible())
        .enabled(d.isEnabled())
        .createdAt(d.getCreatedAt())
        .updatedAt(d.getUpdatedAt())
        .build();
  }

  @Override public Platillo save(Platillo p){ return toDomain(jpa.save(toEntity(p))); }

  @Override public Optional<Platillo> findById(UUID id){ return jpa.findById(id).map(PlatilloRepositorioOutputAdapter::toDomain); }

  @Override public boolean existsByNombreInRestaurante(UUID restauranteId, String nombre){
    return jpa.existsByNombreInRestaurante(restauranteId, nombre);
  }

  @Override public Page<Platillo> list(String q, UUID restauranteId, Boolean enabled, Pageable pageable){
    return jpa.search(q, restauranteId, enabled, pageable).map(PlatilloRepositorioOutputAdapter::toDomain);
  }
}