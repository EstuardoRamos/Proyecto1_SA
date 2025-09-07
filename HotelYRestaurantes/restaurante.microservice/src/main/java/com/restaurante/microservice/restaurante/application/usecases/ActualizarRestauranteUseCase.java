package com.restaurante.microservice.restaurante.application.usecases;

import com.restaurante.microservice.common.errors.AlreadyExistsException;
import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.restaurante.application.inputoports.ActualizarRestauranteInputPort;
import com.restaurante.microservice.restaurante.application.outputports.RestauranteRepositorioPort;
import com.restaurante.microservice.restaurante.domain.Restaurante;
import java.math.BigDecimal;
import java.util.UUID;
//import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public class ActualizarRestauranteUseCase implements ActualizarRestauranteInputPort {

    private final RestauranteRepositorioPort repo;

    public ActualizarRestauranteUseCase(RestauranteRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Restaurante actualizar(UUID id, String nombre, String direccion,
            BigDecimal impuestoPorc, BigDecimal propinaPorcDefault) {
        var r = repo.porId(id).orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));
        if (repo.existsNombreInHotel(nombre, r.getHotelId(), id)) {
            throw new AlreadyExistsException("Nombre duplicado en el mismo hotel");
        }
        r.actualizar(nombre, direccion, impuestoPorc, propinaPorcDefault);
        return repo.guardar(r);
    }
}
