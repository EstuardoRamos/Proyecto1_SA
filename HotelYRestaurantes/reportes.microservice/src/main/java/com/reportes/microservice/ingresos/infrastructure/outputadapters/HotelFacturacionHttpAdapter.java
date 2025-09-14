// ingresos/infrastructure/outputadapters/http/HotelFacturacionHttpAdapter.java
package com.reportes.microservice.ingresos.infrastructure.outputadapters;

import com.reportes.microservice.ingresos.application.outputports.query.FacturacionHotelQueryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class HotelFacturacionHttpAdapter implements FacturacionHotelQueryPort {

  private final WebClient client;
  private static final DateTimeFormatter DATE = DateTimeFormatter.ISO_LOCAL_DATE;

  public HotelFacturacionHttpAdapter(@Value("${hotel.api.base-url}") String baseUrl, WebClient.Builder builder) {
    this.client = builder.baseUrl(baseUrl).build();
  }

  @Override
  public List<FacturaSnapshot> listar(UUID hotelId, Instant desde, Instant hasta) {
    var uriSpec = client.get().uri(uri -> {
      var b = uri.path("/v1/facturas/hotel").queryParam("hotelId", hotelId);
      if (desde != null) b.queryParam("desde", DATE.format(desde.atZone(java.time.ZoneOffset.UTC).toLocalDate()));
      if (hasta != null) b.queryParam("hasta", DATE.format(hasta.atZone(java.time.ZoneOffset.UTC).toLocalDate()));
      return b.build();
    });

    var list = uriSpec.retrieve()
        .onStatus(sc -> sc.is4xxClientError() || sc.is5xxServerError(),
            resp -> resp.createException().flatMap(Mono::error))
        .bodyToFlux(FacturaDto.class)
        .collectList()
        .block();

    if (list == null) return List.of();

    return list.stream().map(d -> new FacturaSnapshot(
        d.id, d.hotelId, d.reservaId, d.clienteId, d.createdAt,
        d.subtotal, d.impuesto, d.propina, d.total, d.estado
    )).toList();
  }

  // DTO alineado al MS Hotel
  static final class FacturaDto {
    public UUID id;
    public UUID hotelId;
    public UUID reservaId;
    public UUID clienteId;
    public Instant createdAt;
    public BigDecimal subtotal;
    public BigDecimal impuesto;
    public BigDecimal propina;
    public BigDecimal total;
    public String estado;
  }
}