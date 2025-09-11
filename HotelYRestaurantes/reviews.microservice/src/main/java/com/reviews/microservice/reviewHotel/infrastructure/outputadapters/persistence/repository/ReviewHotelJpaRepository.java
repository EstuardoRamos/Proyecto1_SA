// infrastructure/outputadapters/persistence/repository/ReviewHotelJpaRepository.java
package com.reviews.microservice.reviewHotel.infrastructure.outputadapters.persistence.repository;

import com.reviews.microservice.reviewHotel.infrastructure.outputadapters.persistence.entity.ReviewHotelDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewHotelJpaRepository extends JpaRepository<ReviewHotelDbEntity, UUID> {

    boolean existsByFacturaHotelId(UUID facturaHotelId);

    List<ReviewHotelDbEntity> findByHotelIdOrderByCreatedAtDesc(UUID hotelId);

    List<ReviewHotelDbEntity> findByFacturaHotelId(UUID facturaHotelId);

    Page<ReviewHotelDbEntity> findByHotelId(UUID hotelId, Pageable p);

    Page<ReviewHotelDbEntity> findByHotelIdAndEnabled(UUID hotelId, boolean enabled, Pageable p);

    @Query("""
  select coalesce(avg(r.estrellas), 0), count(r)
  from ReviewHotelDbEntity r
  where r.hotelId = :hid and r.enabled = true
""")
    Object[] resumen(@Param("hid") UUID hotelId);
}
