package com.hotel.microservice.reserva.application.outputports;

import com.hotel.microservice.reserva.domain.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ReservaRepositorioPort {

    Reserva guardar(Reserva r);

    Optional<Reserva> porId(UUID id);

    Page<Reserva> porHotel(UUID hotelId, Pageable pageable);

    boolean existeTraslape(UUID habitacionId, LocalDate in, LocalDate out, Set<Reserva.Estado> estados);

    void actualizarEstado(UUID id, Reserva.Estado estado);
}
