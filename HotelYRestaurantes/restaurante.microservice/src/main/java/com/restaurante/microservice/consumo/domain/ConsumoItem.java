// domain/ConsumoItem.java
package com.restaurante.microservice.consumo.domain;

import lombok.Getter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class ConsumoItem {
  private final UUID id;
  private final UUID cuentaId;
  private final UUID platilloId;       // opcional (snapshot)
  private final String nombre;         // snapshot nombre
  private final BigDecimal precioUnitario;
  private final int cantidad;
  private final BigDecimal subtotal;   // precioUnitario * cantidad
  private final String nota;           // opcional
  private final Instant createdAt;
  private final Instant updatedAt;

  private ConsumoItem(UUID id, UUID cuentaId, UUID platilloId, String nombre,
                      BigDecimal precioUnitario, int cantidad, BigDecimal subtotal,
                      String nota, Instant createdAt, Instant updatedAt) {
    this.id=id; this.cuentaId=cuentaId; this.platilloId=platilloId; this.nombre=nombre;
    this.precioUnitario=precioUnitario; this.cantidad=cantidad; this.subtotal=subtotal;
    this.nota=nota; this.createdAt=createdAt; this.updatedAt=updatedAt;
  }

  public static ConsumoItem crear(UUID cuentaId, UUID platilloId, String nombre,
                                  BigDecimal precioUnitario, int cantidad, String nota) {
    var now = Instant.now();
    var sub = nz(precioUnitario).multiply(BigDecimal.valueOf(cantidad));
    return new ConsumoItem(UUID.randomUUID(), cuentaId, platilloId, nombre,
        nz(precioUnitario), cantidad, sub, nota, now, now);
  }

  public static ConsumoItem rehidratar(UUID id, UUID cuentaId, UUID platilloId, String nombre,
                                       BigDecimal precioUnitario, int cantidad, BigDecimal subtotal,
                                       String nota, Instant createdAt, Instant updatedAt) {
    return new ConsumoItem(id, cuentaId, platilloId, nombre, nz(precioUnitario),
        cantidad, nz(subtotal), nota, createdAt, updatedAt);
  }

  public ConsumoItem actualizar(BigDecimal precioUnit, Integer cant, String notaNueva) {
    var p = precioUnit != null ? precioUnit : this.precioUnitario;
    var c = cant != null ? cant : this.cantidad;
    var sub = nz(p).multiply(BigDecimal.valueOf(c));
    return new ConsumoItem(this.id, this.cuentaId, this.platilloId, this.nombre, p, c, sub,
        notaNueva != null ? notaNueva : this.nota, this.createdAt, Instant.now());
  }

  private static BigDecimal nz(BigDecimal v) { return v==null? BigDecimal.ZERO : v; }
}