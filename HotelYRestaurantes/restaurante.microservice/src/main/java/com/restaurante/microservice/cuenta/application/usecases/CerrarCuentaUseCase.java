package com.restaurante.microservice.cuenta.application.usecases;

import com.restaurante.microservice.cuenta.application.inputports.CerrarCuentaInputPort;
import com.restaurante.microservice.cuenta.application.ouputports.persistence.CuentaRepositorioPort;
import com.restaurante.microservice.cuenta.domain.Cuenta;
import java.util.UUID;


public class CerrarCuentaUseCase implements CerrarCuentaInputPort {

    private final CuentaRepositorioPort repo;

    public CerrarCuentaUseCase(CuentaRepositorioPort repo) {
        this.repo = repo;
    }

    @Override
    public Cuenta cerrar(UUID id) {
        var c = repo.porId(id).orElseThrow(() -> new com.restaurante.microservice.common.errors.NotFoundException("Cuenta no encontrada"));
        c.cerrar();
        return repo.guardar(c);
    }
}
