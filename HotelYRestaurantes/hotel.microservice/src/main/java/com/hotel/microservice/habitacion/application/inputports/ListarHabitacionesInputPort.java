/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.microservice.habitacion.application.inputports;

import com.hotel.microservice.habitacion.domain.Habitacion;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author estuardoramos
 */
public interface ListarHabitacionesInputPort {

    Page<Habitacion> listar(UUID hotelId, Pageable pageable);
}
