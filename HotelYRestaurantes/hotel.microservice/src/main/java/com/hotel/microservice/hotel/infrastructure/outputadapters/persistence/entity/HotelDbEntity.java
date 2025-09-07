package com.hotel.microservice.hotel.infrastructure.outputadapters.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity @Table(name="hotels")
public class HotelDbEntity {

  @Id
  @JdbcTypeCode(SqlTypes.CHAR)   // UUID como CHAR(36)
  @Column(length = 36)
  private UUID id;

  @Column(nullable=false, unique=true, length=180)
  private String nombre;

  private int estrellas;
  private boolean activo;

  // Direccion (aplanada por simplicidad)
  private String pais;
  private String ciudad;
  private String linea1;
  private String linea2;
  private String codigoPostal;

  // Politicas (aplanadas)
  private String checkInDesde;
  private String checkOutHasta;

  public HotelDbEntity() {}

  // getters/setters
  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }
  public int getEstrellas() { return estrellas; }
  public void setEstrellas(int estrellas) { this.estrellas = estrellas; }
  public boolean isActivo() { return activo; }
  public void setActivo(boolean activo) { this.activo = activo; }
  public String getPais() { return pais; }
  public void setPais(String pais) { this.pais = pais; }
  public String getCiudad() { return ciudad; }
  public void setCiudad(String ciudad) { this.ciudad = ciudad; }
  public String getLinea1() { return linea1; }
  public void setLinea1(String linea1) { this.linea1 = linea1; }
  public String getLinea2() { return linea2; }
  public void setLinea2(String linea2) { this.linea2 = linea2; }
  public String getCodigoPostal() { return codigoPostal; }
  public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
  public String getCheckInDesde() { return checkInDesde; }
  public void setCheckInDesde(String v) { this.checkInDesde = v; }
  public String getCheckOutHasta() { return checkOutHasta; }
  public void setCheckOutHasta(String v) { this.checkOutHasta = v; }
}