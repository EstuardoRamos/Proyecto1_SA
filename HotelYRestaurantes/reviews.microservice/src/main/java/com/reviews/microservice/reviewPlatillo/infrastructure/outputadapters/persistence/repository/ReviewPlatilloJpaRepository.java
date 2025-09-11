// infrastructure/outputadapters/persistence/repository/ReviewPlatilloJpaRepository.java
package com.reviews.microservice.reviewPlatillo.infrastructure.outputadapters.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reviews.microservice.reviewPlatillo.infrastructure.outputadapters.persistence.entity.ReviewPlatilloDbEntity;

public interface ReviewPlatilloJpaRepository extends JpaRepository<ReviewPlatilloDbEntity, UUID> {

    Page<ReviewPlatilloDbEntity> findByPlatilloIdAndEnabledTrue(UUID platilloId, Pageable pageable);

    boolean existsByCuentaIdAndPlatilloId(UUID cuentaId, UUID platilloId);

    // Proyección para evitar Object[]
    record ResumenProjection(double promedio, long total) {

    }

    @Query("""
    select new com.reviews.microservice.reviewPlatillo.infrastructure.outputadapters.persistence.repository.ReviewPlatilloJpaRepository$ResumenProjection(
      coalesce(avg(r.estrellas),0), count(r)
    )
    from ReviewPlatilloDbEntity r
    where r.platilloId = :pid and r.enabled = true
  """)
    ResumenProjection resumen(@Param("pid") UUID platilloId);

    // infrastructure/outputadapters/persistence/repository/ReviewPlatilloJpaRepository.java
    @Query("""
  select r.platilloId as platilloId,
         avg(r.estrellas) as promedio,
         count(r.id)      as total
  from ReviewPlatilloDbEntity r
  where r.restauranteId = :rid
    and r.enabled = true
    and (:desde is null or r.createdAt >= :desde)
    and (:hasta is null or r.createdAt <= :hasta)
  group by r.platilloId
  order by avg(r.estrellas) desc, count(r.id) desc
""")
    List<ResumenPlatilloProjection> resumenPorRestaurante(
            @Param("rid") java.util.UUID restauranteId,
            @Param("desde") java.time.Instant desde,
            @Param("hasta") java.time.Instant hasta
    );
}
