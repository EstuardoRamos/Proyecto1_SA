package com.reportes.microservice.popularidad.infrastructure.outputadapters.http;

import com.reportes.microservice.popularidad.application.outputports.FacturasRestQueryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class FacturasRestHttpAdapter implements FacturasRestQueryPort {

  private final RestClient client;

  public FacturasRestHttpAdapter(
      @Value("${rest.api.base-url}") String baseUrl
  ) {
    this.client = RestClient.builder().baseUrl(baseUrl).build();
  }

  @Override
  public List<FacturaSnap> listar(UUID restauranteId, LocalDate desde, LocalDate hasta ) {

    // OJO: el MS de Restaurantes espera LocalDate "yyyy-MM-dd" en los query params
    List<FacturaDto> dtos = client.get()
        .uri(uriBuilder -> {
          var b = uriBuilder.path("/v1/facturas/restaurantes");
          if (restauranteId != null) b = b.queryParam("restauranteId", restauranteId);
          if (desde != null)        b = b.queryParam("desde", desde); // LocalDate -> "2025-09-01"
          if (hasta != null)        b = b.queryParam("hasta", hasta);
          return b.build();
        })
        .retrieve()
        .onStatus(HttpStatusCode::isError, (req, resp) -> {
          throw new RuntimeException("Error MS Restaurante " + resp.getStatusCode());
        })
        .body(new ParameterizedTypeReference<List<FacturaDto>>() {})
        ;

    if (dtos == null || dtos.isEmpty()) return List.of();

    return dtos.stream().map(this::toSnap).toList();
  }

  private FacturaSnap toSnap(FacturaDto d) {
    return new FacturaSnap(
        d.id, d.restauranteId, d.cuentaId, d.clienteId,
        d.createdAt, d.subtotal, d.impuesto, d.propina, d.total, d.estado
    );
  }

  // DTO para deserializar la respuesta del MS de Restaurantes
  // (usa los mismos nombres que devuelve tu endpoint /v1/facturas/restaurantes)
  static final class FacturaDto {
    public UUID id;
    public UUID restauranteId;
    public UUID cuentaId;
    public UUID clienteId;
    public Instant createdAt;
    public BigDecimal subtotal;
    public BigDecimal impuesto;
    public BigDecimal propina;
    public BigDecimal total;
    public String estado;
  }
}