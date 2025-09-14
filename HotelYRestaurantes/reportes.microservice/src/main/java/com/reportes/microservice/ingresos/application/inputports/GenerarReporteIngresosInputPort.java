// ingresos/application/inputports/GenerarReporteIngresosInputPort.java
package com.reportes.microservice.ingresos.application.inputports;

import com.reportes.microservice.ingresos.application.outputports.query.FacturacionHotelQueryPort;
import com.reportes.microservice.ingresos.application.outputports.query.FacturacionRestQueryPort;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface GenerarReporteIngresosInputPort {

  record ResultadoHotel(UUID hotelId, Instant desde, Instant hasta,
                        BigDecimal total,
                        List<FacturacionHotelQueryPort.FacturaSnapshot> facturas) {}

  record ResultadoRest(UUID restauranteId, Instant desde, Instant hasta,
                       BigDecimal total,
                       List<FacturacionRestQueryPort.FacturaSnapshot> facturas) {}

  ResultadoHotel ingresosHotel(UUID hotelId, Instant desde, Instant hasta);

  ResultadoRest ingresosRestaurante(UUID restauranteId, Instant desde, Instant hasta);
}