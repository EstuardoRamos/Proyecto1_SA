package com.restaurante.microservice.restaurante.infrastrucuture.outputadapters.persistence.respository;

import com.restaurante.microservice.restaurante.infrastrucuture.outputadapters.persistence.entity.RestauranteDbEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RestauranteJpaRepository extends JpaRepository<RestauranteDbEntity, UUID> {

  // derivados simples
  Page<RestauranteDbEntity> findByHotelId(UUID hotelId, Pageable pageable);
  Page<RestauranteDbEntity> findByEnabled(Boolean enabled, Pageable pageable);
  Page<RestauranteDbEntity> findByHotelIdAndEnabled(UUID hotelId, Boolean enabled, Pageable pageable);

  // búsqueda flexible (si la usas)
  @Query("""
     select r from RestauranteDbEntity r
     where (:q is null or lower(r.nombre) like lower(concat('%', :q, '%'))
            or lower(coalesce(r.direccion, '')) like lower(concat('%', :q, '%')))
       and (:hotelId is null or r.hotelId = :hotelId)
       and (:enabled is null or r.enabled = :enabled)
  """)
  Page<RestauranteDbEntity> search(@Param("q") String q,
                                   @Param("hotelId") UUID hotelId,
                                   @Param("enabled") Boolean enabled,
                                   Pageable pageable);

  // para unicidad (más práctico que exists+search)
  Optional<RestauranteDbEntity> findFirstByHotelIdAndNombreIgnoreCase(UUID hotelId, String nombre);
  Optional<RestauranteDbEntity> findFirstByHotelIdIsNullAndNombreIgnoreCase(String nombre);
}