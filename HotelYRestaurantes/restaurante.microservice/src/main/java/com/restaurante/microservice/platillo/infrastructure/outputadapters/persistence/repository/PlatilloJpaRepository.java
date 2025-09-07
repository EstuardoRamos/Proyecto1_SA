package com.restaurante.microservice.platillo.infrastructure.outputadapters.persistence.repository;

import com.restaurante.microservice.platillo.infrastructure.outputadapters.persistence.entity.PlatilloDbEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PlatilloJpaRepository extends JpaRepository<PlatilloDbEntity, UUID>, JpaSpecificationExecutor<PlatilloDbEntity> {

  @Query("""
    select case when count(p) > 0 then true else false end
    from PlatilloDbEntity p
    where p.restauranteId = :restId and lower(p.nombre) = lower(:nombre) and p.enabled = true
  """)
  boolean existsByNombreInRestaurante(@Param("restId") UUID restauranteId, @Param("nombre") String nombre);

  // Búsqueda simple con filtros opcionales
  @Query("""
    select p from PlatilloDbEntity p
    where (:q is null or lower(p.nombre) like lower(concat('%', :q, '%')))
      and (:restId is null or p.restauranteId = :restId)
      and (:enabled is null or p.enabled = :enabled)
  """)
  Page<PlatilloDbEntity> search(@Param("q") String q,
                                @Param("restId") UUID restauranteId,
                                @Param("enabled") Boolean enabled,
                                Pageable pageable);
}