//package com.restaurante.microservice.consumo.infrastructure.outputadapters.persistence.repository;
package com.restaurante.microservice.consumo.infrastructure.outputadapters.persistence.repository;

import com.restaurante.microservice.consumo.infrastructure.outputadapters.persistence.entity.ConsumoDbEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface ConsumoJpaRepository extends JpaRepository<ConsumoDbEntity, UUID> {
  Page<ConsumoDbEntity> findByCuentaId(UUID cuentaId, Pageable pageable);

  @Query("select coalesce(sum(i.subtotal), 0) from ConsumoDbEntity i where i.cuentaId = :cuentaId")
  BigDecimal sumSubtotal(@Param("cuentaId") UUID cuentaId);
}