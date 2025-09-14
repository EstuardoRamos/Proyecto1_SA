package com.restaurante.microservice.facturacion.infraestructure.outputadapters.persistence.repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restaurante.microservice.facturacion.infraestructure.outputadapters.persistence.entity.FacturaRestDbEntity;

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
  select f from FacturaRestDbEntity f
  where (:rid is null or f.restauranteId = :rid)
    and f.createdAt >= coalesce(:desde, f.createdAt)
    and f.createdAt <= coalesce(:hasta, f.createdAt)
  order by f.createdAt desc, f.numero desc
""")
    Page<FacturaRestDbEntity> listar(@Param("rid") UUID restauranteId,
            @Param("desde") Instant desde,
            @Param("hasta") Instant hasta,
            Pageable pageable);
}
