// application/usecases/HabilitarRestauranteUseCase.java
package com.restaurante.microservice.restaurante.application.usecases;

import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.restaurante.application.inputoports.HabilitarRestauranteInputPort;
import com.restaurante.microservice.restaurante.application.outputports.RestauranteRepositorioPort;
import com.restaurante.microservice.restaurante.domain.Restaurante;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Service
@RequiredArgsConstructor
public class HabilitarRestauranteUseCase implements HabilitarRestauranteInputPort {

  private final RestauranteRepositorioPort repo;

  @Transactional
  @Override
  public Restaurante habilitar(UUID id) {
    Restaurante r = repo.porId(id)
        .orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));
    // Si tu dominio tiene método de negocio, usa r.habilitar();
    r.habilitar();
    // si tienes un setUpdatedAt()/touch(), úsalo aquí
    return repo.guardar(r);
  }
}