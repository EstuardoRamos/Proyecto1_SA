package com.hotel.microservice.reserva.application.usecases;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.reserva.application.inputports.CheckInInputPort;
import com.hotel.microservice.reserva.application.outputports.ReservaRepositorioPort;

import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public class CheckInUseCase implements CheckInInputPort {

    private final ReservaRepositorioPort repo;

    public CheckInUseCase(ReservaRepositorioPort repo) {
        this.repo = repo;
    }

    @Override @Transactional
    public void checkIn(UUID id) {
        var r = repo.porId(id).orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
        r.checkIn(); 
        System.out.println("ESTAMOS EN CHECKOUT---------------------------");// valida transición (solo desde RESERVADA)
        repo.actualizarEstado(id, r.getEstado());      // persistir estado CHECKED_IN
        
        System.out.println("ESTAMOS EN CHECkIN---------------------------");
    }
    
}
