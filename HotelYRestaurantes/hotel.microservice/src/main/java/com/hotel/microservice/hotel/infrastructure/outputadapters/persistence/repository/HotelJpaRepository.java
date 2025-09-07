package com.hotel.microservice.hotel.infrastructure.outputadapters.persistence.repository;

import com.hotel.microservice.hotel.infrastructure.outputadapters.persistence.entity.HotelDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface HotelJpaRepository extends JpaRepository<HotelDbEntity, UUID> {

    boolean existsByNombreIgnoreCase(String nombre);

    List<HotelDbEntity> findAllByActivoTrueOrderByNombreAsc();
}
