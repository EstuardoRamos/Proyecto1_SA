// com/reportes/microservice/config/OpenApiConfig.java
package com.reportes.microservice.common.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

  @Bean
  OpenAPI reportesApiInfo() {
    return new OpenAPI().info(new Info()
        .title("Reportes API")
        .version("v1")
        .description("Microservicio de reportes (ingresos, consumos, alojamientos, etc.)"));
  }

  // Agrupa solo tus controladores /v1/**
  @Bean
  GroupedOpenApi reportesGroup() {
    return GroupedOpenApi.builder()
        .group("reportes")
        .pathsToMatch("/v1/**")
        .build();
  }
}