package com.hotel.microservice.reserva.application.inputports;

import com.hotel.microservice.reserva.domain.Reserva;
import java.util.Optional;
import java.util.UUID;

public interface ObtenerReservaInputPort {

    Optional<Reserva> obtener(UUID id);
}
