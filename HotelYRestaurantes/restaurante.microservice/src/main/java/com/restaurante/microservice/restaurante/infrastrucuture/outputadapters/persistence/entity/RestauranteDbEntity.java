package com.restaurante.microservice.restaurante.infrastrucuture.outputadapters.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "restaurants",
  uniqueConstraints = @UniqueConstraint(name="uk_rest_hotel_nombre", columnNames={"hotel_id","nombre"}))
public class RestauranteDbEntity {

  @Id
  @JdbcTypeCode(SqlTypes.CHAR) @Column(length = 36)
  private UUID id;

  @JdbcTypeCode(SqlTypes.CHAR) @Column(name="hotel_id", length=36)
  private UUID hotelId;   // nullable

  @Column(nullable=false, length=150) private String nombre;
  @Column(length=400) private String direccion;
  @Column(name="impuesto_porc", nullable=false, precision=6, scale=4) private BigDecimal impuestoPorc;
  @Column(name="propina_porc_def", nullable=false, precision=6, scale=4) private BigDecimal propinaPorcDefault;
  @Column(nullable=false) private boolean enabled;
  @Column(name="created_at", nullable=false) private Instant createdAt;
  @Column(name="updated_at", nullable=false) private Instant updatedAt;

  // getters/setters
  public UUID getId(){return id;} public void setId(UUID id){this.id=id;}
  public UUID getHotelId(){return hotelId;} public void setHotelId(UUID v){this.hotelId=v;}
  public String getNombre(){return nombre;} public void setNombre(String v){this.nombre=v;}
  public String getDireccion(){return direccion;} public void setDireccion(String v){this.direccion=v;}
  public BigDecimal getImpuestoPorc(){return impuestoPorc;} public void setImpuestoPorc(BigDecimal v){this.impuestoPorc=v;}
  public BigDecimal getPropinaPorcDefault(){return propinaPorcDefault;} public void setPropinaPorcDefault(BigDecimal v){this.propinaPorcDefault=v;}
  public boolean isEnabled(){return enabled;} public void setEnabled(boolean v){this.enabled=v;}
  public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant v){this.createdAt=v;}
  public Instant getUpdatedAt(){return updatedAt;} public void setUpdatedAt(Instant v){this.updatedAt=v;}
}