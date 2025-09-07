package com.hotel.microservice.reserva.application.inputports;

import java.util.UUID;

public interface CancelarReservaInputPort {
    void cancelar(UUID id);
    
}
