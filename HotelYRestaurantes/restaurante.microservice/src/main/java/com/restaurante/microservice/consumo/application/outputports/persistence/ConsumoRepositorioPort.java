// application/outputports/persistence/ConsumoRepositorioPort.java
package com.restaurante.microservice.consumo.application.outputports.persistence;

import com.restaurante.microservice.consumo.domain.ConsumoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface ConsumoRepositorioPort {
  ConsumoItem guardar(ConsumoItem i);
  Optional<ConsumoItem> porId(UUID id);
  void eliminar(UUID id);
  Page<ConsumoItem> listarPorCuenta(UUID cuentaId, Pageable pageable);
  BigDecimal sumarSubtotalPorCuenta(UUID cuentaId); // para recalcular totales
}