package com.hotel.microservice.reserva.application.inputports;

import com.hotel.microservice.reserva.domain.Reserva;

public interface CrearReservaInputPort {

    Reserva crear(Reserva r);
}
