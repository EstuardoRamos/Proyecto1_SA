package com.hotel.microservice.reserva.application.inputports;

import java.util.UUID;

public interface CheckInInputPort {
    void checkIn(UUID id);
}
