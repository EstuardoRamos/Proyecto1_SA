package com.hotel.microservice.reserva.application.usecases;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.reserva.application.inputports.CheckOutInputPort;
import com.hotel.microservice.reserva.application.outputports.ReservaRepositorioPort;

import java.util.UUID;

public class CheckOutUseCase implements CheckOutInputPort {
  private final ReservaRepositorioPort repo;

  public CheckOutUseCase(ReservaRepositorioPort repo) {
    this.repo = repo;
  }

  @Override
  public void checkOut(UUID id) {
    var r = repo.porId(id).orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
    r.checkOut();                                  // valida transición (solo desde CHECKED_IN)
    repo.actualizarEstado(id, r.getEstado());      // persistir estado CHECKED_OUT
  }
}