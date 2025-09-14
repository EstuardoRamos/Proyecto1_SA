// infrastructure/config/PopularidadBeansConfig.java
package com.reportes.microservice.popularidad.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reportes.microservice.popularidad.application.inputports.CalcularHabitacionMasPopularPort;
import com.reportes.microservice.popularidad.application.inputports.CalcularRestauranteMasPopularPort;
import com.reportes.microservice.popularidad.application.outputports.FacturasRestQueryPort;
import com.reportes.microservice.popularidad.application.outputports.ReservasQueryPort;
import com.reportes.microservice.popularidad.application.usecases.CalcularHabitacionMasPopularUseCase;
import com.reportes.microservice.popularidad.application.usecases.CalcularRestauranteMasPopularUseCase;

@Configuration
public class PopularidadBeansConfig {

    @Bean
    public CalcularHabitacionMasPopularPort calcularHabitacionMasPopular(ReservasQueryPort reservas) {
        return new CalcularHabitacionMasPopularUseCase(reservas);
    }

    @Bean
    public CalcularRestauranteMasPopularPort calcularRestauranteMasPopular(FacturasRestQueryPort facturas) {
        return new CalcularRestauranteMasPopularUseCase(facturas);
    }
}
