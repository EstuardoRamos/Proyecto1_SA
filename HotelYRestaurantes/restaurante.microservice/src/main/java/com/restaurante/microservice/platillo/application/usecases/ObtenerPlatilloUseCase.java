package com.restaurante.microservice.platillo.application.usecases;

import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.platillo.application.inputports.ObtenerPlatilloInputPort;
import com.restaurante.microservice.platillo.application.outputports.persistence.PlatilloRepositorioOutputPort;
import com.restaurante.microservice.platillo.domain.Platillo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ObtenerPlatilloUseCase implements ObtenerPlatilloInputPort {

    private final PlatilloRepositorioOutputPort repo;

    @Override
    public Platillo obtener(UUID id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Platillo no encontrado"));
    }
}
