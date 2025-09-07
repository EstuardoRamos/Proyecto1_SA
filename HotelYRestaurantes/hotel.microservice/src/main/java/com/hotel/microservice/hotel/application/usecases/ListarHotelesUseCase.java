package com.hotel.microservice.hotel.application.usecases;

import com.hotel.microservice.hotel.application.inputports.ListarHotelesInputPort;
import com.hotel.microservice.hotel.application.outputports.HotelRepositorioPort;
import com.hotel.microservice.hotel.domain.Hotel;
import java.util.List;

public class ListarHotelesUseCase implements ListarHotelesInputPort {

    private final HotelRepositorioPort repo;

    public ListarHotelesUseCase(HotelRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public List<Hotel> listar() {
        return repo.listarActivos();
    }
}
