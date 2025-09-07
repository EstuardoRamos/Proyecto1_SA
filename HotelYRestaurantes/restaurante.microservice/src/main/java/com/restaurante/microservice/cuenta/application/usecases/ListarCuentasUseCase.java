package com.restaurante.microservice.cuenta.application.usecases;

// ListarCuentasUseCase.java
import com.restaurante.microservice.cuenta.application.inputports.ListarCuentasInputPort;
import com.restaurante.microservice.cuenta.application.ouputports.persistence.CuentaRepositorioPort;
import com.restaurante.microservice.cuenta.domain.Cuenta;
import com.restaurante.microservice.cuenta.domain.EstadoCuenta;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ListarCuentasUseCase implements ListarCuentasInputPort {

    private final CuentaRepositorioPort repo;

    @Override
    public Page<Cuenta> listar(UUID restauranteId, EstadoCuenta estado, Pageable p) {
        return repo.listar(restauranteId, estado, p);
    }
}
