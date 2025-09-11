// infrastructure/outputadapters/http/FacturacionHttpAdapter.java
package com.reviews.microservice.reviewHotel.infrastructure.outputadapters.http;

import com.reviews.microservice.reviewHotel.application.outputports.query.FacturacionQueryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Component
public class FacturacionHttpAdapter implements FacturacionQueryPort {

  private final WebClient client;

  public FacturacionHttpAdapter(
      @Value("${hotel.api.base-url}") String baseUrl,
      WebClient.Builder builder
  ) {
    this.client = builder
        .baseUrl(baseUrl)
        .build();
  }

  @Override
public Optional<FacturaHotelSnapshot> facturaHotel(UUID facturaHotelId) {
  try {
    FacturaHotelDto dto = client.get()
        .uri("/v1/facturas/hotel/{id}", facturaHotelId)
        .exchangeToMono(resp -> {
          var sc = resp.statusCode();                // HttpStatusCode
          if (sc.is2xxSuccessful()) {
            return resp.bodyToMono(FacturaHotelDto.class);
          }
          if (sc.value() == 404) {                  // NO encontrada => Optional.empty()
            return reactor.core.publisher.Mono.empty();
          }
          // Cualquier otro código => propaga excepción
          return resp.createException().flatMap(reactor.core.publisher.Mono::error);
        })
        .timeout(java.time.Duration.ofSeconds(5))
        .block();

    if (dto == null) return Optional.empty();

    // OJO: el snapshot debe recibir también el id de la factura
    return Optional.of(new FacturacionQueryPort.FacturaHotelSnapshot(
         dto.hotelId, dto.habitacionId, dto.clienteId, dto.estado
    ));
  } catch (Exception e) {
    throw new RuntimeException("Error consultando MS Hotel: " + e.getMessage(), e);
  }
}

  // Debe MATCHEAR los campos que devuelve el MS Hotel en /v1/facturas/hotel/{id}
  static final class FacturaHotelDto {
    public UUID id;
    public UUID hotelId;
    public UUID habitacionId; // puede venir null
    public UUID clienteId;    // puede venir null
    public String estado;     // "EMITIDA", etc.
  }
}