/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.microservice.hotel.application.inputports;

import com.hotel.microservice.hotel.domain.Hotel;
import java.util.UUID;

/**
 *
 * @author estuardoramos
 */
public interface ActualizarHotelInputPort {
  Hotel actualizar(UUID id, String nombre, Integer estrellas, Hotel.Direccion dir, Hotel.Politicas pol);
}

