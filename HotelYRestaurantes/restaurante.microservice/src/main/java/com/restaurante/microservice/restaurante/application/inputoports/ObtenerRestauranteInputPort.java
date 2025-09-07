package com.restaurante.microservice.restaurante.application.inputoports;

import com.restaurante.microservice.restaurante.domain.Restaurante;
import java.util.Optional;
import java.util.UUID;

public interface ObtenerRestauranteInputPort {

    Optional<Restaurante> obtener(UUID id);
}
