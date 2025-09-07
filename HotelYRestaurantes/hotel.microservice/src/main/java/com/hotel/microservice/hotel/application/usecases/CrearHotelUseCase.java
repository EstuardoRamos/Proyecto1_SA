package com.hotel.microservice.hotel.application.usecases;

import com.hotel.microservice.common.errors.AlreadyExistsException;
import com.hotel.microservice.hotel.application.inputports.CrearHotelInputPort;
import com.hotel.microservice.hotel.application.outputports.HotelRepositorioPort;
import com.hotel.microservice.hotel.domain.Hotel;

public class CrearHotelUseCase implements CrearHotelInputPort {

    private final HotelRepositorioPort repo;

    public CrearHotelUseCase(HotelRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Hotel crear(String nombre, int estrellas, Hotel.Direccion dir, Hotel.Politicas pol) {
        if (repo.existePorNombre(nombre)) {
            throw new AlreadyExistsException("El hotel ya existe");
        }
        var h = new Hotel(null, nombre, estrellas, dir, pol, null, null, true);
        return repo.guardar(h);
    }
}
