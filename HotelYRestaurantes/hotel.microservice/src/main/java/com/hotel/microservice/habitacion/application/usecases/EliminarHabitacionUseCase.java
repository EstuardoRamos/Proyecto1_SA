/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.microservice.habitacion.application.usecases;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.habitacion.application.inputports.EliminarHabitacionInputPort;
import com.hotel.microservice.habitacion.application.outputports.HabitacionRepositorioPort;
import java.util.UUID;

/**
 *
 * @author estuardoramos
 */
public class EliminarHabitacionUseCase implements EliminarHabitacionInputPort {

    private final HabitacionRepositorioPort repo;

    public EliminarHabitacionUseCase(HabitacionRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public void eliminar(UUID id) {
        repo.porId(id).orElseThrow(() -> new NotFoundException("Habitación no encontrada"));
        repo.eliminar(id);
    }
}
