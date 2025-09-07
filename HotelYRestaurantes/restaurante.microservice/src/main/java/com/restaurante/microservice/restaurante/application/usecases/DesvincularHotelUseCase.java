package com.restaurante.microservice.restaurante.application.usecases;

import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.restaurante.application.inputoports.DesvincularHotelInputPort;
import com.restaurante.microservice.restaurante.application.outputports.RestauranteRepositorioPort;
import com.restaurante.microservice.restaurante.domain.Restaurante;
import java.util.UUID;

public class DesvincularHotelUseCase implements DesvincularHotelInputPort {

    private final RestauranteRepositorioPort repo;

    public DesvincularHotelUseCase(RestauranteRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Restaurante desvincular(UUID restauranteId) {
        var r = repo.porId(restauranteId).orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));
        r.desvincularHotel();
        return repo.guardar(r);
    }
}
