package com.restaurante.microservice.platillo.application.usecases;

import com.restaurante.microservice.platillo.application.inputports.ListarPlatillosInputPort;
import com.restaurante.microservice.platillo.application.outputports.persistence.PlatilloRepositorioOutputPort;
import com.restaurante.microservice.platillo.domain.Platillo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListarPlatillosUseCase implements ListarPlatillosInputPort {

    private final PlatilloRepositorioOutputPort repo;

    @Override
    public Page<Platillo> listar(String q, UUID restauranteId, Boolean enabled, Pageable pageable) {
        return repo.list(q, restauranteId, enabled, pageable);
    }
}
