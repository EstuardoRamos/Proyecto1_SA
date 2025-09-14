package com.reportes.microservice.popularidad.application.outputports;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FacturasRestQueryPort {

  // Snapshot mínimo de una factura de restaurante
  record FacturaSnap(
      UUID id,
      UUID restauranteId,
      UUID cuentaId,
      UUID clienteId,
      Instant createdAt,
      BigDecimal subtotal,
      BigDecimal impuesto,
      BigDecimal propina,
      BigDecimal total,
      String estado
  ) {}

  List<FacturaSnap> listar(UUID restauranteId, LocalDate desde, LocalDate hasta );
}