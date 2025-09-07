/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.microservice.hotel.application.inputports;

import com.hotel.microservice.hotel.domain.Hotel;

/**
 *
 * @author estuardoramos
 */
public interface CrearHotelInputPort {
  Hotel crear(String nombre, int estrellas, Hotel.Direccion dir, Hotel.Politicas pol);   
}
