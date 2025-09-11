// infrastructure/config/ReviewPlatilloBeansConfig.java
package com.reviews.microservice.reviewPlatillo.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reviews.microservice.reviewPlatillo.application.inputports.CrearReviewPlatilloInputPort;
import com.reviews.microservice.reviewPlatillo.application.inputports.ListarReviewsPlatilloInputPort;
import com.reviews.microservice.reviewPlatillo.application.inputports.ObtenerReviewPlatilloInputPort;
import com.reviews.microservice.reviewPlatillo.application.inputports.ResumenReviewsPlatilloInputPort;
import com.reviews.microservice.reviewPlatillo.application.outputports.persistence.ReviewPlatilloRepositorioPort;
import com.reviews.microservice.reviewPlatillo.application.outputports.query.RestauranteCuentaQueryPort;
import com.reviews.microservice.reviewPlatillo.application.usecases.CrearReviewPlatilloUseCase;
import com.reviews.microservice.reviewPlatillo.application.usecases.ListarReviewsPlatilloUseCase;
import com.reviews.microservice.reviewPlatillo.application.usecases.ObtenerReviewPlatilloUseCase;
import com.reviews.microservice.reviewPlatillo.application.usecases.ResumenReviewsPlatilloUseCase;

@Configuration
public class ReviewPlatilloBeansConfig {

    @Bean
    public CrearReviewPlatilloInputPort crearReviewPlatillo(ReviewPlatilloRepositorioPort repo, RestauranteCuentaQueryPort cuentas) {
        return new CrearReviewPlatilloUseCase(repo, cuentas);
    }

    @Bean
    public ListarReviewsPlatilloInputPort listarReviewsPlatillo(ReviewPlatilloRepositorioPort repo) {
        return new ListarReviewsPlatilloUseCase(repo);
    }

    @Bean
    public ObtenerReviewPlatilloInputPort obtenerReviewPlatillo(ReviewPlatilloRepositorioPort repo) {
        return new ObtenerReviewPlatilloUseCase(repo);
    }

    @Bean
    public ResumenReviewsPlatilloInputPort resumenReviewsPlatillo(ReviewPlatilloRepositorioPort repo) {
        return new ResumenReviewsPlatilloUseCase(repo);
    }
}
