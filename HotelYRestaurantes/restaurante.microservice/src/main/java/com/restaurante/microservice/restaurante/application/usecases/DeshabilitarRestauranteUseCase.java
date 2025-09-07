package com.restaurante.microservice.restaurante.application.usecases;

import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.restaurante.application.inputoports.DeshabilitarRestauranteInputPort;
import com.restaurante.microservice.restaurante.application.outputports.RestauranteRepositorioPort;
import java.util.UUID;

public class DeshabilitarRestauranteUseCase implements DeshabilitarRestauranteInputPort {

    private final RestauranteRepositorioPort repo;

    public DeshabilitarRestauranteUseCase(RestauranteRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public void deshabilitar(UUID id) {
        var r = repo.porId(id).orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));
        r.deshabilitar();
        repo.guardar(r);
    }
}
