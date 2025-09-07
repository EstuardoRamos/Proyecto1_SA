package com.hotel.microservice.reserva.application.inputports;

import java.util.UUID;

public interface CheckOutInputPort {

    void checkOut(UUID id);
}
