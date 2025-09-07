package com.restaurante.microservice.facturacion.infraestructure.outputadapters.persistence.repository;

import com.restaurante.microservice.facturacion.infraestructure.outputadapters.persistence.entity.FacturaRestDbEntity;
//import com.restaurante.microservice.facturacion.infrastructure.outputadapters.persistence.entity.FacturaRestDbEntity;
import java.time.Instant;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface FacturaRestJpaRepository extends JpaRepository<FacturaRestDbEntity, UUID> {

    Optional<FacturaRestDbEntity> findByCuentaId(UUID cuentaId);

    @Query("select coalesce(max(f.numero),0) from FacturaRestDbEntity f where f.restauranteId=:rid and f.serie=:serie")
    int maxNumero(@Param("rid") UUID restauranteId, @Param("serie") String serie);

    @Query("""
     select f from FacturaRestDbEntity f
     where f.restauranteId = :rid
       and (:desde is null or f.createdAt >= :desde)
       and (:hasta is null or f.createdAt <= :hasta)
     order by f.createdAt desc, f.numero desc
  """)
    List<FacturaRestDbEntity> listar(@Param("rid") UUID restauranteId,
            @Param("desde") Instant desde,
            @Param("hasta") Instant hasta);
}
