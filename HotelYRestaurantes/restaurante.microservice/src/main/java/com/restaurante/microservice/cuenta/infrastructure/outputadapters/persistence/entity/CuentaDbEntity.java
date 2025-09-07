package com.restaurante.microservice.cuenta.infrastructure.outputadapters.persistence.entity;

import com.restaurante.microservice.cuenta.domain.EstadoCuenta;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter @Setter
@Entity @Table(name = "restaurant_accounts")
public class CuentaDbEntity {

  @Id
  @JdbcTypeCode(SqlTypes.CHAR) @Column(length = 36)
  private UUID id;

  @JdbcTypeCode(SqlTypes.CHAR) @Column(name="restaurante_id", length = 36, nullable = false)
  private UUID restauranteId;

  @Column(length = 50)
  private String mesa;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private EstadoCuenta estado;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal subtotal;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal impuesto;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal propina;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal total;

  @Column(name="created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name="updated_at")
  private Instant updatedAt;
}