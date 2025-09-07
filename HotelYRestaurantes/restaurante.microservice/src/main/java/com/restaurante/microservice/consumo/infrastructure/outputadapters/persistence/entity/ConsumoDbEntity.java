package com.restaurante.microservice.consumo.infrastructure.outputadapters.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter @Setter
@Entity @Table(name="restaurant_account_items")
public class ConsumoDbEntity {

  @Id @JdbcTypeCode(SqlTypes.CHAR) @Column(length=36)
  private UUID id;

  @JdbcTypeCode(SqlTypes.CHAR) @Column(name="cuenta_id", length=36, nullable=false)
  private UUID cuentaId;

  @JdbcTypeCode(SqlTypes.CHAR) @Column(name="platillo_id", length=36)
  private UUID platilloId;

  @Column(nullable=false, length=200)
  private String nombre;

  @Column(name="precio_unitario", nullable=false, precision=12, scale=2)
  private BigDecimal precioUnitario;

  @Column(nullable=false)
  private int cantidad;

  @Column(nullable=false, precision=12, scale=2)
  private BigDecimal subtotal;

  @Column(length=500)
  private String nota;

  @Column(name="created_at", nullable=false, updatable=false)
  private Instant createdAt;

  @Column(name="updated_at")
  private Instant updatedAt;
}