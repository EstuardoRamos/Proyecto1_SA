package com.restaurante.microservice.cuenta.application.inputports;

import com.restaurante.microservice.cuenta.domain.Cuenta;
import com.restaurante.microservice.cuenta.domain.EstadoCuenta;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface ListarCuentasInputPort {

    Page<Cuenta> listar(UUID restauranteId, EstadoCuenta estado, org.springframework.data.domain.Pageable pageable);
}
