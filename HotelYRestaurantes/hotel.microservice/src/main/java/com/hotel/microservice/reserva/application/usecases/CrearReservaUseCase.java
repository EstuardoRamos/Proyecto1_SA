package com.hotel.microservice.reserva.application.usecases;

import com.hotel.microservice.common.errors.AlreadyExistsException;
import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.reserva.application.inputports.CrearReservaInputPort;
import com.hotel.microservice.reserva.application.outputports.HabitacionQueryPort;
import com.hotel.microservice.reserva.application.outputports.ReservaRepositorioPort;
import com.hotel.microservice.reserva.domain.Reserva;

import java.math.BigDecimal;
import java.util.Set;

public class CrearReservaUseCase implements CrearReservaInputPort {
  private final ReservaRepositorioPort reservas;
  private final HabitacionQueryPort habitaciones;

  public CrearReservaUseCase(ReservaRepositorioPort reservas, HabitacionQueryPort habitaciones) {
    this.reservas = reservas; this.habitaciones = habitaciones;
  }

  @Override
  public Reserva crear(Reserva r) {
    if (!habitaciones.disponibleParaReservar(r.getHabitacionId()))
      throw new NotFoundException("Habitación no disponible");
    var estadosActivos = Set.of(Reserva.Estado.RESERVADA, Reserva.Estado.CHECKED_IN);
    if (reservas.existeTraslape(r.getHabitacionId(), r.getEntrada(), r.getSalida(), estadosActivos))
      throw new AlreadyExistsException("Ya existe una reserva para ese rango");

    var precioNoche = habitaciones.precioBase(r.getHabitacionId());
    var total = precioNoche.multiply(BigDecimal.valueOf(r.noches()));
    r.setTotal(total);
    return reservas.guardar(r);
  }
}