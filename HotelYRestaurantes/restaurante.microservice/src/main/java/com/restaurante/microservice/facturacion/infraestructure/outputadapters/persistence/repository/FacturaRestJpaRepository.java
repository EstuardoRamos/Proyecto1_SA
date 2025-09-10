package com.restaurante.microservice.facturacion.infraestructure.outputadapters.persistence.repository;

import com.restaurante.microservice.facturacion.infraestructure.outputadapters.persistence.entity.FacturaRestDbEntity;
import java.time.Instant;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.*;

public interface FacturaRestJpaRepository extends JpaRepository<FacturaRestDbEntity, UUID> {

  Optional<FacturaRestDbEntity> findByCuentaId(UUID cuentaId);

  @Query("""
    select coalesce(max(f.numero), 0)
    from FacturaRestDbEntity f
    where f.restauranteId = :rid and f.serie = :serie
  """)
  int maxNumero(@Param("rid") UUID restauranteId, @Param("serie") String serie);

  // ✅ acepta con o sin rango de fechas (si vienen null lista todo)
  @EntityGraph(attributePaths = "items", type = EntityGraph.EntityGraphType.LOAD)
  @Query("""
    select f
    from FacturaRestDbEntity f
    where f.restauranteId = :rid
      and f.createdAt >= coalesce(:desde, f.createdAt)
      and f.createdAt <= coalesce(:hasta, f.createdAt)
    order by f.createdAt desc, f.numero desc
  """)
  Page<FacturaRestDbEntity> listar(@Param("rid") UUID restauranteId,
                                   @Param("desde") Instant desde,
                                   @Param("hasta") Instant hasta,
                                   Pageable pageable);
}