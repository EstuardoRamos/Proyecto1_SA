// infrastructure/outputadapters/persistence/repository/ReviewHotelJpaRepository.java
package com.reviews.microservice.reviewHotel.infrastructure.outputadapters.persistence.repository;

import com.reviews.microservice.reviewHotel.infrastructure.outputadapters.persistence.entity.ReviewHotelDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewHotelJpaRepository extends JpaRepository<ReviewHotelDbEntity, UUID> {

    boolean existsByFacturaHotelId(UUID facturaHotelId);

    List<ReviewHotelDbEntity> findByHotelIdOrderByCreatedAtDesc(UUID hotelId);

    List<ReviewHotelDbEntity> findByFacturaHotelId(UUID facturaHotelId);
}
