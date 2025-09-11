// infrastructure/outputadapters/http/RestauranteCuentaHttpAdapter.java
package com.reviews.microservice.reviewPlatillo.infrastructure.outputadapters.http;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.reviews.microservice.reviewPlatillo.application.outputports.query.RestauranteCuentaQueryPort;

import reactor.core.publisher.Mono;

@Component
public class RestauranteCuentaHttpAdapter implements RestauranteCuentaQueryPort {

    private final WebClient client;

    public RestauranteCuentaHttpAdapter(@Value("${restaurante.api.base-url}") String baseUrl) {
        this.client = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public Optional<CuentaSnapshot> cuenta(UUID cuentaId) {
        try {
            var dto = client.get()
                    .uri("/v1/cuentas/{id}", cuentaId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, resp
                            -> resp.statusCode().value() == 404 ? Mono.empty() : resp.createException())
                    .bodyToMono(CuentaDto.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            if (dto == null) {
                return Optional.empty();
            }
            return Optional.of(new CuentaSnapshot(dto.id, dto.restauranteId, dto.clienteId)); // clienteId puede venir null
        } catch (WebClientResponseException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean cuentaContienePlatillo(UUID cuentaId, UUID platilloId) {
        try {
            var lista = client.get()
                    .uri("/v1/cuentas/{id}/consumos?size=1000", cuentaId)
                    .retrieve()
                    .bodyToFlux(ConsumoDto.class)
                    .collectList()
                    .timeout(Duration.ofSeconds(5))
                    .block();

            if (lista == null) {
                return false;
            }
            return lista.stream().anyMatch(c -> platilloId.equals(c.platilloId));
        } catch (Exception e) {
            throw new RuntimeException("Error consultando consumos de la cuenta", e);
        }
    }

    // DTOs
    static final class CuentaDto {

        public UUID id;
        public UUID restauranteId;
        public UUID clienteId;
    }

    static final class ConsumoDto {

        public UUID id;
        public UUID platilloId;
        public String nombre;
    }
}
