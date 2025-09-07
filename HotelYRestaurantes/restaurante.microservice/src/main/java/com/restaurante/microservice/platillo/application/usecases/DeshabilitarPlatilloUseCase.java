package com.restaurante.microservice.platillo.application.usecases;

import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.platillo.application.inputports.DeshabilitarPlatilloInputPort;
import com.restaurante.microservice.platillo.application.outputports.persistence.PlatilloRepositorioOutputPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeshabilitarPlatilloUseCase implements DeshabilitarPlatilloInputPort {

    private final PlatilloRepositorioOutputPort repo;

    @Override
    public void deshabilitar(UUID id) {
        var p = repo.findById(id).orElseThrow(() -> new NotFoundException("Platillo no encontrado"));
        p.deshabilitar();
        repo.save(p);
    }
}
