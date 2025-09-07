package com.hotel.microservice.hotel.application.usecases;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.hotel.application.inputports.ObtenerHotelInputPort;
import com.hotel.microservice.hotel.application.outputports.HotelRepositorioPort;
import com.hotel.microservice.hotel.domain.Hotel;
import java.util.UUID;

public class ObtenerHotelUseCase implements ObtenerHotelInputPort {

    private final HotelRepositorioPort repo;

    public ObtenerHotelUseCase(HotelRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Hotel obtener(UUID id) {
        return repo.buscarPorId(id).orElseThrow(() -> new NotFoundException("Hotel no encontrado"));
    }
}
