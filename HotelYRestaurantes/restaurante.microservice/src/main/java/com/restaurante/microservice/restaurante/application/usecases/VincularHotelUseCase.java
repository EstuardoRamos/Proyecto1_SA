package com.restaurante.microservice.restaurante.application.usecases;

import com.restaurante.microservice.common.errors.AlreadyExistsException;
import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.restaurante.application.inputoports.VincularHotelInputPort;
import com.restaurante.microservice.restaurante.application.outputports.RestauranteRepositorioPort;
import com.restaurante.microservice.restaurante.domain.Restaurante;
import java.util.UUID;

public class VincularHotelUseCase implements VincularHotelInputPort {

    private final RestauranteRepositorioPort repo;

    public VincularHotelUseCase(RestauranteRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Restaurante vincular(UUID restauranteId, UUID hotelId) {
        var r = repo.porId(restauranteId).orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));
        if (repo.existsNombreInHotel(r.getNombre(), hotelId, restauranteId)) {
            throw new AlreadyExistsException("Nombre duplicado en el hotel destino");
        }
        r.vincularHotel(hotelId);
        return repo.guardar(r);
    }
}
