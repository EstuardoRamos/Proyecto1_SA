package com.restaurante.microservice.cuenta.application.inputports;

import com.restaurante.microservice.cuenta.domain.Cuenta;
import java.util.Optional;
import java.util.UUID;

public interface ObtenerCuentaInputPort {

    Optional<Cuenta> porId(UUID id);
}
