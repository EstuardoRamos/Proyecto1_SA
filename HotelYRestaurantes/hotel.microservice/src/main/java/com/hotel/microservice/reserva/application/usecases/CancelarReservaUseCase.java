package com.hotel.microservice.reserva.application.usecases;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.reserva.application.inputports.CancelarReservaInputPort;
import com.hotel.microservice.reserva.application.outputports.ReservaRepositorioPort;

import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public class CancelarReservaUseCase implements CancelarReservaInputPort {

    private final ReservaRepositorioPort repo;

    public CancelarReservaUseCase(ReservaRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public void cancelar(UUID id) {
        var r = repo.porId(id).orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
        r.cancelar();                                  // valida regla de dominio
        repo.actualizarEstado(id, r.getEstado());      // persistir estado CANCELADA
    }
}
