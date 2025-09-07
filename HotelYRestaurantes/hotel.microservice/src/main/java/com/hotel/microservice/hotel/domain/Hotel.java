package com.hotel.microservice.hotel.domain;

import java.util.UUID;
import java.util.List;

public class Hotel {
  private final UUID id;
  private String nombre;
  private int estrellas;                // 1..5
  private boolean activo;
  private Direccion direccion;          // VO
  private Politicas politicas;          // VO
  private List<String> amenities;       // opcional
  private List<String> imagenes;        // opcional

  public Hotel(UUID id, String nombre, int estrellas,
               Direccion direccion, Politicas politicas,
               List<String> amenities, List<String> imagenes,
               boolean activo) {
    if (estrellas < 1 || estrellas > 5) throw new IllegalArgumentException("estrellas 1..5");
    this.id = (id == null) ? UUID.randomUUID() : id;
    this.nombre = nombre;
    this.estrellas = estrellas;
    this.direccion = direccion;
    this.politicas = politicas;
    this.amenities = amenities;
    this.imagenes = imagenes;
    this.activo = activo;
  }

  public UUID getId() { return id; }
  public String getNombre() { return nombre; }
  public int getEstrellas() { return estrellas; }
  public boolean isActivo() { return activo; }
  public Direccion getDireccion() { return direccion; }
  public Politicas getPoliticas() { return politicas; }
  public List<String> getAmenities() { return amenities; }
  public List<String> getImagenes() { return imagenes; }

  public void actualizarDatos(String nombre, Integer estrellas, Direccion direccion, Politicas politicas) {
    if (nombre != null && !nombre.isBlank()) this.nombre = nombre;
    if (estrellas != null) {
      if (estrellas < 1 || estrellas > 5) throw new IllegalArgumentException("estrellas 1..5");
      this.estrellas = estrellas;
    }
    if (direccion != null) this.direccion = direccion;
    if (politicas != null) this.politicas = politicas;
  }

  public void desactivar() { this.activo = false; }
  public void activar() { this.activo = true; }

  // Value Objects (anidados para cumplir “un archivo por dominio”)
  public record Direccion(String pais, String ciudad, String linea1, String linea2, String codigoPostal) {}
  public record Politicas(String checkInDesde, String checkOutHasta) {}
}