package com.restaurante.microservice.restaurante.application.usecases;

import com.restaurante.microservice.common.errors.AlreadyExistsException;
import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.restaurante.application.inputoports.CrearRestauranteInputPort;
import com.restaurante.microservice.restaurante.application.outputports.RestauranteRepositorioPort;
import com.restaurante.microservice.restaurante.domain.Restaurante;
import java.math.BigDecimal;
import java.util.UUID;

public class CrearRestauranteUseCase implements CrearRestauranteInputPort {

    private final RestauranteRepositorioPort repo;

    public CrearRestauranteUseCase(RestauranteRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Restaurante crear(UUID hotelId, String nombre, String direccion,
            BigDecimal impuestoPorc, BigDecimal propinaPorcDefault) {
        if (repo.existsNombreInHotel(nombre, hotelId, null)) {
            throw new AlreadyExistsException("Ya existe un restaurante con ese nombre en el hotel");
        }
        var r = Restaurante.crear(hotelId, nombre, direccion, impuestoPorc, propinaPorcDefault);
        return repo.guardar(r);
    }
}
