
package com.restaurante.microservice.facturacion.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Factura {
  private final UUID id;
  private final UUID restauranteId;
  private final UUID cuentaId;
  private final String moneda;
  private final String serie;
  private final Integer numero;
  private final String clienteNit;
  private final String clienteNombre;
  private final BigDecimal subtotal;
  private final BigDecimal impuesto;
  private final BigDecimal propina;
  private final BigDecimal total;
  private final Instant createdAt;
  private final EstadoFactura estado;
  private final List<FacturaItem> items;

  private Factura(UUID id, UUID restauranteId, UUID cuentaId, String moneda, String serie, Integer numero,
                  String clienteNit, String clienteNombre, BigDecimal subtotal, BigDecimal impuesto,
                  BigDecimal propina, BigDecimal total, Instant createdAt, EstadoFactura estado,
                  List<FacturaItem> items) {
    this.id=id; this.restauranteId=restauranteId; this.cuentaId=cuentaId; this.moneda=moneda;
    this.serie=serie; this.numero=numero; this.clienteNit=clienteNit; this.clienteNombre=clienteNombre;
    this.subtotal=subtotal; this.impuesto=impuesto; this.propina=propina; this.total=total;
    this.createdAt=createdAt; this.estado=estado; this.items=List.copyOf(items);
  }

  public static Factura emitir(UUID restauranteId, UUID cuentaId, String moneda, String serie, Integer numero,
                               String clienteNit, String clienteNombre,
                               BigDecimal subtotal, BigDecimal impuesto, BigDecimal propina, BigDecimal total,
                               List<FacturaItem> items) {
    return new Factura(UUID.randomUUID(), restauranteId, cuentaId, moneda, serie, numero,
        clienteNit, clienteNombre, nz(subtotal), nz(impuesto), nz(propina), nz(total),
        Instant.now(), EstadoFactura.EMITIDA, new ArrayList<>(items));
  }

  public static Factura rehidratar(UUID id, UUID restauranteId, UUID cuentaId, String moneda, String serie, Integer numero,
                                   String clienteNit, String clienteNombre, BigDecimal subtotal, BigDecimal impuesto,
                                   BigDecimal propina, BigDecimal total, Instant createdAt, EstadoFactura estado,
                                   List<FacturaItem> items) {
    return new Factura(id, restauranteId, cuentaId, moneda, serie, numero, clienteNit, clienteNombre,
        subtotal, impuesto, propina, total, createdAt, estado, items);
  }

  public Factura anular() {
    return new Factura(this.id, this.restauranteId, this.cuentaId, this.moneda, this.serie, this.numero,
        this.clienteNit, this.clienteNombre, this.subtotal, this.impuesto, this.propina, this.total,
        this.createdAt, EstadoFactura.ANULADA, this.items);
  }

  private static BigDecimal nz(BigDecimal v){ return v==null? BigDecimal.ZERO : v; }
}