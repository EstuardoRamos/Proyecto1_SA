package com.restaurante.microservice.cuenta.application.usecases;

import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.cuenta.application.inputports.CobrarCuentaInputPort;
import com.restaurante.microservice.cuenta.application.ouputports.persistence.CuentaRepositorioPort;
import com.restaurante.microservice.cuenta.domain.Cuenta;
import java.util.UUID;
//import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
public class CobrarCuentaUseCase implements CobrarCuentaInputPort {

    private final CuentaRepositorioPort repo;

    public CobrarCuentaUseCase(CuentaRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Cuenta cobrar(UUID id) {
        var c = repo.porId(id).orElseThrow(() -> {
            return new NotFoundException("Cuenta no encontrada");
        });
        c.cobrar();
        return repo.guardar(c);
    }
}
