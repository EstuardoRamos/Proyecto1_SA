// infrastructure/outputadapters/persistence/repository/FacturaHotelJpaRepository.java
package com.hotel.microservice.facturacion.infrastructure.outputadapters.persistence.repository;

import com.hotel.microservice.facturacion.infrastructure.outputadapters.persistence.entity.FacturaHotelDbEntity;
import java.time.Instant;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface FacturaHotelJpaRepository extends JpaRepository<FacturaHotelDbEntity, java.util.UUID> {

    Optional<FacturaHotelDbEntity> findByReservaId(java.util.UUID reservaId);

    @Query("select coalesce(max(f.numero),0) from FacturaHotelDbEntity f where f.hotelId=:hid and f.serie=:serie")
    int maxNumero(@Param("hid") java.util.UUID hotelId, @Param("serie") String serie);

    @Query("""
     select f from FacturaHotelDbEntity f
     where f.hotelId = :hid
       and (:desde is null or f.createdAt >= :desde)
       and (:hasta is null or f.createdAt <= :hasta)
     order by f.createdAt desc, f.numero desc
  """)
    java.util.List<FacturaHotelDbEntity> listar(@Param("hid") java.util.UUID hotelId,
            @Param("desde") Instant desde,
            @Param("hasta") Instant hasta);
}
