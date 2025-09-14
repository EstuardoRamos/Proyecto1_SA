// ingresos/infrastructure/config/IngresosBeansConfig.java
package com.reportes.microservice.ingresos.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reportes.microservice.ingresos.application.inputports.GenerarReporteIngresosInputPort;
import com.reportes.microservice.ingresos.application.outputports.query.FacturacionHotelQueryPort;
import com.reportes.microservice.ingresos.application.outputports.query.FacturacionRestQueryPort;
import com.reportes.microservice.ingresos.application.usecases.GenerarReporteIngresosUseCase;

@Configuration
public class IngresosBeansConfig {

  @Bean
  public GenerarReporteIngresosInputPort generarReporteIngresos(
      FacturacionHotelQueryPort hotel,
      FacturacionRestQueryPort rest
  ) {
    return new GenerarReporteIngresosUseCase(hotel, rest);
  }
}