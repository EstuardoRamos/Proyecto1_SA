package com.restaurante.microservice.cuenta.infrastructure.outputadapters.persistence.repository;

import com.restaurante.microservice.cuenta.infrastructure.outputadapters.persistence.entity.CuentaDbEntity;
import com.restaurante.microservice.cuenta.domain.EstadoCuenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CuentaJpaRepository extends JpaRepository<CuentaDbEntity, UUID> {

    Page<CuentaDbEntity> findByRestauranteId(UUID restauranteId, Pageable pageable);

    Page<CuentaDbEntity> findByRestauranteIdAndEstado(UUID restauranteId, EstadoCuenta estado, Pageable pageable);

    boolean existsByRestauranteIdAndMesaAndEstado(UUID restauranteId, String mesa, EstadoCuenta estado); // evitar 2 abiertas en misma mesa
}
