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
public interface ObtenerHotelInputPort {
  Hotel obtener(UUID id);
}


