// infrastructure/config/ReviewHotelBeansConfig.java
package com.reviews.microservice.reviewHotel.infrastructure.config;

import com.reviews.microservice.reviewHotel.application.inputports.*;
import com.reviews.microservice.reviewHotel.application.outputports.persistence.ReviewHotelRepositorioPort;
import com.reviews.microservice.reviewHotel.application.outputports.query.FacturacionQueryPort;
import com.reviews.microservice.reviewHotel.application.usecases.*;
import com.reviews.microservice.reviewHotel.infrastructure.outputadapters.http.FacturacionHttpAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.util.Optional;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Configuration
public class ReviewHotelBeansConfig {

    // === STUB por defecto (para que funcione YA sin errores) ===
    @Bean
    @ConditionalOnProperty(name = "reviews.facturacion.mode", havingValue = "stub", matchIfMissing = true)
    @Primary
    public FacturacionQueryPort facturacionStub(
            @Value("${reviews.facturacion.stub.hotel-id:00000000-0000-0000-0000-000000000001}") UUID hotelIdDefault
    ) {
        return facturaId -> Optional.of(
                new FacturacionQueryPort.FacturaHotelSnapshot(hotelIdDefault, null, null, "EMITIDA")
        );
    }

    @Bean
    @ConditionalOnProperty(name = "reviews.facturacion.mode", havingValue = "http")
    @Primary
    public FacturacionQueryPort facturacionHttp(FacturacionHttpAdapter http) {
        return http;
    }

    // Si luego quieres el HTTP real, crea otro bean (sin @Primary) o bajo un profile.
    // @Bean
    // public FacturacionQueryPort facturacionHttp(FacturacionHttpAdapter http){ return http; }
    @Bean
    public CrearReviewHotelInputPort crearReviewHotel(
            ReviewHotelRepositorioPort repo, FacturacionQueryPort fact) {
        return new CrearReviewHotelUseCase(repo, fact);
    }

    @Bean
    public ListarReviewsHotelInputPort listarReviewsHotel(ReviewHotelRepositorioPort repo) {
        return new ListarReviewsHotelUseCase(repo);
    }

    @Bean
    public ObtenerPromedioHotelInputPort obtenerPromedioHotel(ReviewHotelRepositorioPort repo) {
        return new ObtenerPromedioHotelUseCase(repo);
    }

    @Bean
    public ObtenerReviewHotelInputPort obtenerReviewHotel(ReviewHotelRepositorioPort repo) {
        return new ObtenerReviewHotelUseCase(repo);
    }


    @Bean
    public ResumenReviewsHotelInputPort resumenReviewsHotel(ReviewHotelRepositorioPort repo) {
        return new ResumenReviewsHotelUseCase(repo);
    }

    @Bean
    public TopReviewsHotelInputPort topReviewsHotel(ReviewHotelRepositorioPort repo) {
        return new TopReviewsHotelUseCase(repo);
    }

    @Bean
    public HabilitarReviewHotelInputPort habilitarReviewHotel(ReviewHotelRepositorioPort repo) {
        return new HabilitarReviewHotelUseCase(repo);
    }

    @Bean
    public DeshabilitarReviewHotelInputPort deshabilitarReviewHotel(ReviewHotelRepositorioPort repo) {
        return new DeshabilitarReviewHotelUseCase(repo);
    }
}
