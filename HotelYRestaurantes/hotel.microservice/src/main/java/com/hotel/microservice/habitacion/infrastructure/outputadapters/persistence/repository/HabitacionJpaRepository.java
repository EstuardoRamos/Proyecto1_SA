package com.hotel.microservice.habitacion.infrastructure.outputadapters.persistence.repository;

import com.hotel.microservice.habitacion.infrastructure.outputadapters.persistence.entity.HabitacionDbEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HabitacionJpaRepository extends JpaRepository<HabitacionDbEntity, UUID> {

    Page<HabitacionDbEntity> findByHotelId(UUID hotelId, Pageable pageable);

    boolean existsByHotelIdAndNumero(UUID hotelId, String numero);
}
