package com.hotel.microservice.reserva.application.inputports;

import com.hotel.microservice.reserva.domain.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ListarReservasInputPort {

    Page<Reserva> listar(UUID hotelId, Pageable pageable);
}
