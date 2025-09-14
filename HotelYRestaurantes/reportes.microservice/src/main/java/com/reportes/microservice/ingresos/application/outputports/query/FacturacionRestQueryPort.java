// ingresos/application/outputports/query/FacturacionRestQueryPort.java
package com.reportes.microservice.ingresos.application.outputports.query;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface FacturacionRestQueryPort {
  record FacturaSnapshot(
      UUID id, UUID restauranteId, UUID cuentaId, UUID clienteId,
      Instant createdAt,
      BigDecimal subtotal, BigDecimal impuesto, BigDecimal propina, BigDecimal total,
      String estado
  ) {}

  List<FacturaSnapshot> listar(UUID restauranteId, Instant desde, Instant hasta);
}