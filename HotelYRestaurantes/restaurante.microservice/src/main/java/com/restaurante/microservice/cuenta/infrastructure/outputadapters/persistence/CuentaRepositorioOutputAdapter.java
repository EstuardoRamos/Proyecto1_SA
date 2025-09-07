package com.restaurante.microservice.cuenta.infrastructure.outputadapters.persistence;

import com.restaurante.microservice.cuenta.application.ouputports.persistence.CuentaRepositorioPort;
import com.restaurante.microservice.cuenta.domain.Cuenta;
//import com.restaurante.microservice.cuenta.application.outputports.persistence.CuentaRepositorioPort;
import com.restaurante.microservice.cuenta.domain.EstadoCuenta;
import com.restaurante.microservice.cuenta.infrastructure.outputadapters.persistence.entity.CuentaDbEntity;
import com.restaurante.microservice.cuenta.infrastructure.outputadapters.persistence.repository.CuentaJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CuentaRepositorioOutputAdapter implements CuentaRepositorioPort {
  private final CuentaJpaRepository jpa;
  public CuentaRepositorioOutputAdapter(CuentaJpaRepository jpa){ this.jpa = jpa; }

  private static Cuenta toDomain(CuentaDbEntity e) {
    return Cuenta.rehidratar(
        e.getId(), e.getRestauranteId(), e.getMesa(), e.getEstado(),
        e.getSubtotal(), e.getImpuesto(), e.getPropina(), e.getTotal(),
        e.getCreatedAt(), e.getUpdatedAt()
    );
  }
  private static CuentaDbEntity toEntity(Cuenta c) {
    var e = new CuentaDbEntity();
    e.setId(c.getId());
    e.setRestauranteId(c.getRestauranteId());
    e.setMesa(c.getMesa());
    e.setEstado(c.getEstado());
    e.setSubtotal(c.getSubtotal());
    e.setImpuesto(c.getImpuesto());
    e.setPropina(c.getPropina());
    e.setTotal(c.getTotal());
    e.setCreatedAt(c.getCreatedAt());
    e.setUpdatedAt(c.getUpdatedAt());
    return e;
  }

  @Override public Cuenta guardar(Cuenta c) { return toDomain(jpa.save(toEntity(c))); }
  @Override public Optional<Cuenta> porId(UUID id) { return jpa.findById(id).map(CuentaRepositorioOutputAdapter::toDomain); }

  @Override
  public Page<Cuenta> listar(UUID restauranteId, EstadoCuenta estado, Pageable pageable) {
    var page = (restauranteId == null)
        ? jpa.findAll(pageable)
        : (estado == null
            ? jpa.findByRestauranteId(restauranteId, pageable)
            : jpa.findByRestauranteIdAndEstado(restauranteId, estado, pageable));
    return page.map(CuentaRepositorioOutputAdapter::toDomain);
  }

  @Override
  public boolean existeCuentaAbiertaMismaMesa(UUID restauranteId, String mesa) {
    if (mesa == null || mesa.isBlank()) return false;
    return jpa.existsByRestauranteIdAndMesaAndEstado(restauranteId, mesa, EstadoCuenta.ABIERTA);
  }
}