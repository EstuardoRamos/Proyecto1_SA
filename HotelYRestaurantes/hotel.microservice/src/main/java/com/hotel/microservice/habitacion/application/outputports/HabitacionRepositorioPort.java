package com.hotel.microservice.habitacion.application.outputports;

import com.hotel.microservice.habitacion.domain.Habitacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface HabitacionRepositorioPort {

    Habitacion guardar(Habitacion h);

    Optional<Habitacion> porId(UUID id);

    Page<Habitacion> porHotel(UUID hotelId, Pageable pageable);

    boolean existeNumeroEnHotel(UUID hotelId, String numero);

    void eliminar(UUID id);
}
