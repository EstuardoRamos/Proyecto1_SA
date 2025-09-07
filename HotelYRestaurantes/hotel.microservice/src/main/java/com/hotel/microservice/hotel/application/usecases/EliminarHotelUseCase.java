package com.hotel.microservice.hotel.application.usecases;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.hotel.application.inputports.EliminarHotelInputPort;
import com.hotel.microservice.hotel.application.outputports.HotelRepositorioPort;
import java.util.UUID;

public class EliminarHotelUseCase implements EliminarHotelInputPort {

    private final HotelRepositorioPort repo;

    public EliminarHotelUseCase(HotelRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public void eliminar(UUID id) {
        repo.buscarPorId(id).orElseThrow(() -> new NotFoundException("Hotel no encontrado"));
        repo.desactivar(id);
    }
}
