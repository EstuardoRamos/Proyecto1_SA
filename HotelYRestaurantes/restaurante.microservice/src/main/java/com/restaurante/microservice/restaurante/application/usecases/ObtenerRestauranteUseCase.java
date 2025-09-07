package com.restaurante.microservice.restaurante.application.usecases;

import com.restaurante.microservice.restaurante.application.inputoports.ObtenerRestauranteInputPort;
import com.restaurante.microservice.restaurante.application.outputports.RestauranteRepositorioPort;
import com.restaurante.microservice.restaurante.domain.Restaurante;
import java.util.Optional;
import java.util.UUID;

public class ObtenerRestauranteUseCase implements ObtenerRestauranteInputPort {

    private final RestauranteRepositorioPort repo;

    public ObtenerRestauranteUseCase(RestauranteRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Optional<Restaurante> obtener(UUID id) {
        return repo.porId(id);
    }
}
