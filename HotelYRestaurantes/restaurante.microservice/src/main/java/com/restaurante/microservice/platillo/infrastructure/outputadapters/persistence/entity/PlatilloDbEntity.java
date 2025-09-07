package com.restaurante.microservice.platillo.infrastructure.outputadapters.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "platillos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlatilloDbEntity {

  @Id
  @JdbcTypeCode(SqlTypes.CHAR) @Column(length = 36)
  private UUID id;

  @JdbcTypeCode(SqlTypes.CHAR) @Column(length = 36)
  private UUID restauranteId;     // puede ser null

  @Column(nullable = false, length = 150)
  private String nombre;

  @Column(length = 500)
  private String descripcion;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal precio;

  @Column(length = 500)
  private String imagenUrl;

  @Column(nullable = false)
  private boolean disponible;

  @Column(nullable = false)
  private boolean enabled;

  @Column(nullable = false)
  private Instant createdAt;

  @Column(nullable = false)
  private Instant updatedAt;
}