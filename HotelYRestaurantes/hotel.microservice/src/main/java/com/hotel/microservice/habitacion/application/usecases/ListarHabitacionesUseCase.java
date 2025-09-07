/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.microservice.habitacion.application.usecases;

import com.hotel.microservice.habitacion.application.inputports.ListarHabitacionesInputPort;
import com.hotel.microservice.habitacion.application.outputports.HabitacionRepositorioPort;
import com.hotel.microservice.habitacion.domain.Habitacion;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author estuardoramos
 */
public class ListarHabitacionesUseCase implements ListarHabitacionesInputPort {

    private final HabitacionRepositorioPort repo;

    public ListarHabitacionesUseCase(HabitacionRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Page<Habitacion> listar(UUID hotelId, Pageable pageable) {
        return repo.porHotel(hotelId, pageable);
    }
}
