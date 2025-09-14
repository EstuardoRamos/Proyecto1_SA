package com.reportes.microservice.popularidad.infrastructure.outputadapters.http;

import com.reportes.microservice.popularidad.application.outputports.ReservasQueryPort;
import com.reportes.microservice.popularidad.application.outputports.ReservasQueryPort.ReservaSnapshot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adaptador HTTP para consultar reservas del MS de Hotel.
 */
@Component
public class ReservasHttpAdapter implements ReservasQueryPort {

  private final WebClient client;

  public ReservasHttpAdapter(WebClient.Builder builder,
                             @Value("${hotel.api.base-url}") String baseUrl) {
    this.client = builder.baseUrl(baseUrl).build();
  }

  @Override
  public List<ReservaSnapshot> listar(LocalDate desde, LocalDate hasta, UUID hotelId) {
    // 1) Intento como ARRAY: /v1/reservas?hotelId=...
    List<ReservaDto> items = fetchArray(hotelId);

    // 2) Si viene vacío, intento como PAGE: /v1/reservas?hotelId=...&page=0&size=1000
    if (items.isEmpty()) {
      items = fetchPage(hotelId);
    }

    // 3) Mapeo y filtro por fechas (si tu MS Hotel aún no filtra por fecha allí)
    return items.stream()
        .map(ReservasHttpAdapter::toSnapshot)
        .filter(r -> (desde == null || !r.entrada().isBefore(desde)) &&
                     (hasta == null || !r.entrada().isAfter(hasta)))
        .collect(Collectors.toList());
  }

  // -------- helpers --------

  private List<ReservaDto> fetchArray(UUID hotelId) {
    try {
      List<ReservaDto> body = client.get()
          .uri(uri -> uri.path("/v1/reservas")
              .queryParam("hotelId", hotelId)
              .build())
          .exchangeToMono(resp -> {
            if (resp.statusCode().is2xxSuccessful()) {
              return resp.bodyToMono(new ParameterizedTypeReference<List<ReservaDto>>() {});
            }
            if (isNotFound(resp.statusCode())) {
              return Mono.empty();
            }
            return resp.createException().flatMap(Mono::error);
          })
          .block();

      return body != null ? body : List.of();
    } catch (Exception e) {
      // Si tu build desea tolerancia a cambios de contrato, retorna vacío en vez de fallar:
      // return List.of();
      throw new RuntimeException("Error consultando reservas (array): " + e.getMessage(), e);
    }
  }

  private List<ReservaDto> fetchPage(UUID hotelId) {
    try {
      PageReservaDto page = client.get()
          .uri(uri -> uri.path("/v1/reservas")
              .queryParam("hotelId", hotelId)
              .queryParam("page", 0)
              .queryParam("size", 1000)
              .build())
          .exchangeToMono(resp -> {
            if (resp.statusCode().is2xxSuccessful()) {
              return resp.bodyToMono(PageReservaDto.class);
            }
            if (isNotFound(resp.statusCode())) {
              return Mono.empty();
            }
            return resp.createException().flatMap(Mono::error);
          })
          .block();

      if (page == null || page.content == null) return List.of();
      return page.content;
    } catch (Exception e) {
      // idem comentario en fetchArray
      // return List.of();
      throw new RuntimeException("Error consultando reservas (page): " + e.getMessage(), e);
    }
  }

  private static boolean isNotFound(HttpStatusCode code) {
    return code != null && code.value() == 404;
  }

  private static ReservaSnapshot toSnapshot(ReservaDto d) {
    return new ReservaSnapshot(
        d.id,
        d.hotelId,
        d.habitacionId,
        d.clienteId,
        d.entrada,
        d.salida
    );
  }

  // ----- DTOs esperados del MS de Hotel -----

  static final class ReservaDto {
    public UUID id;
    public UUID hotelId;
    public UUID habitacionId;
    public UUID clienteId;
    public LocalDate entrada;
    public LocalDate salida;
  }

  static final class PageReservaDto {
    public List<ReservaDto> content = new ArrayList<>();
  }
}