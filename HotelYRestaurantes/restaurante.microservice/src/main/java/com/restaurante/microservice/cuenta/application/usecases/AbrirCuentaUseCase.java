package com.restaurante.microservice.cuenta.application.usecases;

import com.restaurante.microservice.cuenta.application.inputports.AbrirCuentaInputPort;
import com.restaurante.microservice.cuenta.application.ouputports.persistence.CuentaRepositorioPort;
import com.restaurante.microservice.cuenta.domain.Cuenta;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

// AbrirCuentaUseCase.java
@RequiredArgsConstructor
public class AbrirCuentaUseCase implements AbrirCuentaInputPort {

    private final CuentaRepositorioPort repo;

    @Override
    public Cuenta abrir(UUID restauranteId, String mesa) {
        if (repo.existeCuentaAbiertaMismaMesa(restauranteId, mesa)) {
            throw new com.restaurante.microservice.common.errors.AlreadyExistsException("Ya existe una cuenta ABIERTA en esa mesa");
        }
        var c = Cuenta.abrir(restauranteId, mesa);
        return repo.guardar(c);
    }
}
