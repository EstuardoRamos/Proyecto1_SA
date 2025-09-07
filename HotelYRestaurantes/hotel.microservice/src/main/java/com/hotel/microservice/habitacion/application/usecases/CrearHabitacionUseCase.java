package com.hotel.microservice.habitacion.application.usecases;

import com.hotel.microservice.habitacion.application.inputports.*;
import com.hotel.microservice.habitacion.application.outputports.HabitacionRepositorioPort;
import com.hotel.microservice.habitacion.domain.Habitacion;
import com.hotel.microservice.common.errors.AlreadyExistsException;


public class CrearHabitacionUseCase implements CrearHabitacionInputPort {

    private final HabitacionRepositorioPort repo;

    public CrearHabitacionUseCase(HabitacionRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Habitacion crear(Habitacion h) {
        if (repo.existeNumeroEnHotel(h.getHotelId(), h.getNumero())) {
            throw new AlreadyExistsException("Ya existe una habitación con ese número en el hotel");
        }
        return repo.guardar(h);
    }
}
