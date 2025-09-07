package com.hotel.microservice.hotel.application.usecases;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.hotel.application.inputports.ActualizarHotelInputPort;
import com.hotel.microservice.hotel.application.outputports.HotelRepositorioPort;
import com.hotel.microservice.hotel.domain.Hotel;
import java.util.UUID;

public class ActualizarHotelUseCase implements ActualizarHotelInputPort {

    private final HotelRepositorioPort repo;

    public ActualizarHotelUseCase(HotelRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Hotel actualizar(UUID id, String nombre, Integer estrellas, Hotel.Direccion dir, Hotel.Politicas pol) {
        var h = repo.buscarPorId(id).orElseThrow(() -> new NotFoundException("Hotel no encontrado"));
        h.actualizarDatos(nombre, estrellas, dir, pol);
        return repo.guardar(h);
    }
}
