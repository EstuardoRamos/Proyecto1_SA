package com.hotel.microservice.reserva.application.outputports;

import java.math.BigDecimal;
import java.util.UUID;

public interface HabitacionQueryPort {
  BigDecimal precioBase(UUID habitacionId);        // leer desde hexágono habitación
  boolean disponibleParaReservar(UUID habitacionId);
}