/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.microservice.habitacion.application.inputports;

import com.hotel.microservice.habitacion.domain.Habitacion;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author estuardoramos
 */
public interface ObtenerHabitacionInputPort {
  Optional<Habitacion> obtener(UUID id);
}
