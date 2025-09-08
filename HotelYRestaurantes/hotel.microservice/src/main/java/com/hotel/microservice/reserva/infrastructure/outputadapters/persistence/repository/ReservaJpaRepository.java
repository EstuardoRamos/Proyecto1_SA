package com.hotel.microservice.reserva.infrastructure.outputadapters.persistence.repository;

import com.hotel.microservice.reserva.infrastructure.outputadapters.persistence.entity.ReservaDbEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservaJpaRepository extends JpaRepository<ReservaDbEntity, UUID> {

  Page<ReservaDbEntity> findByHotelId(UUID hotelId, Pageable pageable);

  @Query("""
    select count(r)>0 from ReservaDbEntity r
    where r.habitacionId = :roomId
      and r.estado in (:estados)
      and r.entrada < :hasta
      and r.salida  > :desde
  """)
  boolean existsOverlap(@Param("roomId") UUID roomId,
                        @Param("desde") LocalDate desde,
                        @Param("hasta") LocalDate hasta,
                        @Param("estados") List<String> estados);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("update ReservaDbEntity r set r.estado = :estado where r.id = :id")
  int updateEstado(@Param("id") UUID id, @Param("estado") String estado);
}