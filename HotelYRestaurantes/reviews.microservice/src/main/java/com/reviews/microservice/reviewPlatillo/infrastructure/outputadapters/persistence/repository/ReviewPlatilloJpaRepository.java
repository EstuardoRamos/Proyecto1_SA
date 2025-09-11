// infrastructure/outputadapters/persistence/repository/ReviewPlatilloJpaRepository.java
package com.reviews.microservice.reviewPlatillo.infrastructure.outputadapters.persistence.repository;

import com.reviews.microservice.reviewPlatillo.infrastructure.outputadapters.persistence.entity.ReviewPlatilloDbEntity;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

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
}
