package com.hotel.microservice.reserva.application.usecases;

import com.hotel.microservice.reserva.application.inputports.ObtenerReservaInputPort;
import com.hotel.microservice.reserva.application.outputports.ReservaRepositorioPort;
import com.hotel.microservice.reserva.domain.Reserva;

import java.util.Optional;
import java.util.UUID;

public class ObtenerReservaUseCase implements ObtenerReservaInputPort {
  private final ReservaRepositorioPort repo;

  public ObtenerReservaUseCase(ReservaRepositorioPort repo) {
    this.repo = repo;
  }

  @Override
  public Optional<Reserva> obtener(UUID id) {
    return repo.porId(id);
  }
}