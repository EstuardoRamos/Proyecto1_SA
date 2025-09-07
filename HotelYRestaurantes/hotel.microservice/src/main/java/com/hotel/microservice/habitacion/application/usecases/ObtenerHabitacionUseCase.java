/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.microservice.habitacion.application.usecases;

import com.hotel.microservice.habitacion.application.inputports.ObtenerHabitacionInputPort;
import com.hotel.microservice.habitacion.application.outputports.HabitacionRepositorioPort;
import com.hotel.microservice.habitacion.domain.Habitacion;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author estuardoramos
 */
public class ObtenerHabitacionUseCase implements ObtenerHabitacionInputPort {

    private final HabitacionRepositorioPort repo;

    public ObtenerHabitacionUseCase(HabitacionRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Optional<Habitacion> obtener(UUID id) {
        return repo.porId(id);
    }
}
