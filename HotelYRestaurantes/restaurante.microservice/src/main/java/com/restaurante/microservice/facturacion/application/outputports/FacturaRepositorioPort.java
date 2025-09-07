// com/restaurante/microservice/facturacion/application/outputports/persistence/FacturaRepositorioPort.java
package com.restaurante.microservice.facturacion.application.outputports;

import com.restaurante.microservice.facturacion.domain.Factura;
import java.time.Instant; import java.util.*;

public interface FacturaRepositorioPort {
  Factura guardar(Factura f);
  Optional<Factura> porId(UUID id);
  Optional<Factura> porCuentaId(UUID cuentaId);              // idempotencia
  List<Factura> listar(UUID restauranteId, Instant desde, Instant hasta);
  int maxNumeroEnSerie(UUID restauranteId, String serie);    // para siguiente número
}