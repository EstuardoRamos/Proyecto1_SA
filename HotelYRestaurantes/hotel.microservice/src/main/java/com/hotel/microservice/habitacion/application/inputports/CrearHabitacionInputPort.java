package com.hotel.microservice.habitacion.application.inputports;

import com.hotel.microservice.habitacion.domain.Habitacion;

public interface CrearHabitacionInputPort {

    Habitacion crear(Habitacion h);
}
