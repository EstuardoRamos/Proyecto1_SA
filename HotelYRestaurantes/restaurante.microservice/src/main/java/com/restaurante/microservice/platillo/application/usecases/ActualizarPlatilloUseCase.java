package com.restaurante.microservice.platillo.application.usecases;

import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.platillo.application.inputports.ActualizarPlatilloInputPort;
import com.restaurante.microservice.platillo.application.outputports.persistence.PlatilloRepositorioOutputPort;
import com.restaurante.microservice.platillo.domain.Platillo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActualizarPlatilloUseCase implements ActualizarPlatilloInputPort {

  private final PlatilloRepositorioOutputPort repo;

  @Override
  public Platillo actualizar(UUID id, String nombre, String descripcion,
                             BigDecimal precio, String imagenUrl, Boolean disponible) {
    var p = repo.findById(id).orElseThrow(() -> new NotFoundException("Platillo no encontrado"));
    p.actualizar(nombre, descripcion, precio, imagenUrl, disponible);
    return repo.save(p);
    // Si tu adapter hace upsert, bastaría con save; si no, crea un método específico.
  }
}