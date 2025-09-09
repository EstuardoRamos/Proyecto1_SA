// infrastructure/outputadapters/persistence/repository/FacturaHotelJpaRepository.java
package com.hotel.microservice.facturacion.infrastructure.outputadapters.persistence.repository;

import com.hotel.microservice.facturacion.infrastructure.outputadapters.persistence.entity.FacturaHotelDbEntity;
import java.time.Instant;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface FacturaHotelJpaRepository extends JpaRepository<FacturaHotelDbEntity, java.util.UUID> {

    @Query("""
      select f
      from FacturaHotelDbEntity f
      left join fetch f.items
      where f.id = :id
  """)
    Optional<FacturaHotelDbEntity> findDetailById(@Param("id") UUID id);

    // Para detalle (con ítems)
    @EntityGraph(attributePaths = "items")
    Optional<FacturaHotelDbEntity> findByReservaId(UUID reservaId);

    @Query("select coalesce(max(f.numero),0) from FacturaHotelDbEntity f where f.hotelId=:hid and f.serie=:serie")
    int maxNumero(@Param("hid") UUID hotelId, @Param("serie") String serie);

    // LISTADO: filtros opcionales (sin items)
  @Query("""
     select f from FacturaHotelDbEntity f
     where f.hotelId = :hotelId
  """)
  List<FacturaHotelDbEntity> listar(@Param("hotelId") UUID hotelId
                                    );
}
