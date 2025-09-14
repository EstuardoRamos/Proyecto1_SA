// ingresos/application/outputports/query/FacturacionHotelQueryPort.java
package com.reportes.microservice.ingresos.application.outputports.query;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface FacturacionHotelQueryPort {
  record FacturaSnapshot(
      UUID id, UUID hotelId, UUID reservaId, UUID clienteId,
      Instant createdAt,
      BigDecimal subtotal, BigDecimal impuesto, BigDecimal propina, BigDecimal total,
      String estado
  ) {}

  List<FacturaSnapshot> listar(UUID hotelId, Instant desde, Instant hasta);
}