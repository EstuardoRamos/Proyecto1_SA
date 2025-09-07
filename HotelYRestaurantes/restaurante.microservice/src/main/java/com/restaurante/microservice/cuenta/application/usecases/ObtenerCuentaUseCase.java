package com.restaurante.microservice.cuenta.application.usecases;

import com.restaurante.microservice.cuenta.application.inputports.ObtenerCuentaInputPort;
import com.restaurante.microservice.cuenta.application.ouputports.persistence.CuentaRepositorioPort;
import com.restaurante.microservice.cuenta.domain.Cuenta;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
public class ObtenerCuentaUseCase implements ObtenerCuentaInputPort {

    private final CuentaRepositorioPort repo;

    public ObtenerCuentaUseCase(CuentaRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Optional<Cuenta> porId(UUID id) {
        return repo.porId(id);
    }
}
