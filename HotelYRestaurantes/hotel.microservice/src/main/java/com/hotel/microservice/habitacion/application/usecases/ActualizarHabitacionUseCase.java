/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel.microservice.habitacion.application.usecases;

import com.hotel.microservice.common.errors.AlreadyExistsException;
import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.habitacion.application.inputports.ActualizarHabitacionInputPort;
import com.hotel.microservice.habitacion.application.outputports.HabitacionRepositorioPort;
import com.hotel.microservice.habitacion.domain.Habitacion;
import java.util.UUID;

/**
 *
 * @author estuardoramos
 */
public class ActualizarHabitacionUseCase implements ActualizarHabitacionInputPort {

    private final HabitacionRepositorioPort repo;

    public ActualizarHabitacionUseCase(HabitacionRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Habitacion actualizar(UUID id, Habitacion cambios) {
        var actual = repo.porId(id).orElseThrow(() -> new NotFoundException("Habitación no encontrada"));
        if (!actual.getNumero().equals(cambios.getNumero())
                && repo.existeNumeroEnHotel(actual.getHotelId(), cambios.getNumero())) {
            throw new AlreadyExistsException("El número ya está usado en este hotel");
        }
        actual.setNumero(cambios.getNumero());
        actual.setTipo(cambios.getTipo());
        actual.setCapacidad(cambios.getCapacidad());
        actual.setPrecioBase(cambios.getPrecioBase());
        actual.cambiarEstado(cambios.getEstado());
        return repo.guardar(actual);
    }
}
