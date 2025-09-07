package com.hotel.microservice.reserva.application.usecases;

import com.hotel.microservice.reserva.application.inputports.ListarReservasInputPort;
import com.hotel.microservice.reserva.application.outputports.ReservaRepositorioPort;
import com.hotel.microservice.reserva.domain.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class ListarReservasUseCase implements ListarReservasInputPort {
  private final ReservaRepositorioPort repo;

  public ListarReservasUseCase(ReservaRepositorioPort repo) {
    this.repo = repo;
  }

  @Override
  public Page<Reserva> listar(UUID hotelId, Pageable pageable) {
    return repo.porHotel(hotelId, pageable);
  }
}