/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.microservice.habitacion.application.usecases;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.habitacion.application.inputports.CambiarEstadoHabitacionInputPort;
import com.hotel.microservice.habitacion.application.outputports.HabitacionRepositorioPort;
import com.hotel.microservice.habitacion.domain.Habitacion;
import java.util.UUID;

/**
 *
 * @author estuardoramos
 */
public class CambiarEstadoHabitacionUseCase implements CambiarEstadoHabitacionInputPort {

    private final HabitacionRepositorioPort repo;

    public CambiarEstadoHabitacionUseCase(HabitacionRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Habitacion cambiarEstado(UUID id, Habitacion.Estado estado) {
        var h = repo.porId(id).orElseThrow(() -> new NotFoundException("Habitación no encontrada"));
        h.cambiarEstado(estado);
        return repo.guardar(h);
    }
}
