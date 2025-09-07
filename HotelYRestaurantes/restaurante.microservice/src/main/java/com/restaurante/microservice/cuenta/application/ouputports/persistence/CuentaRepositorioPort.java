package com.restaurante.microservice.cuenta.application.ouputports.persistence;

import com.restaurante.microservice.cuenta.domain.Cuenta;
import com.restaurante.microservice.cuenta.domain.EstadoCuenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CuentaRepositorioPort {
  Cuenta guardar(Cuenta c);
  Optional<Cuenta> porId(UUID id);
  Page<Cuenta> listar(UUID restauranteId, EstadoCuenta estado, Pageable pageable);
  boolean existeCuentaAbiertaMismaMesa(UUID restauranteId, String mesa);
}