package com.hotel.microservice.reserva.infrastructure.outputadapters;

import com.hotel.microservice.habitacion.infrastructure.outputadapters.persistence.entity.HabitacionDbEntity;
import com.hotel.microservice.habitacion.infrastructure.outputadapters.persistence.repository.HabitacionJpaRepository;
import com.hotel.microservice.reserva.application.outputports.HabitacionQueryPort;
import org.springframework.stereotype.Component;
import java.math.BigDecimal; import java.util.UUID;

@Component
public class HabitacionQueryJpaAdapter implements HabitacionQueryPort {

  private final HabitacionJpaRepository rooms;

  public HabitacionQueryJpaAdapter(HabitacionJpaRepository rooms) { this.rooms = rooms; }

  @Override
  public BigDecimal precioBase(UUID habitacionId) {
    return rooms.findById(habitacionId)
        .map(HabitacionDbEntity::getPrecioBase)
        .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
  }

  @Override
  public boolean disponibleParaReservar(UUID habitacionId) {
    return rooms.findById(habitacionId)
        .map(e -> !"MANTENIMIENTO".equalsIgnoreCase(e.getEstado()))
        .orElse(false);
  }
}