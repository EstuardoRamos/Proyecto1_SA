package com.restaurante.microservice.cuenta.application.inputports;



import com.restaurante.microservice.cuenta.domain.Cuenta;
import java.util.UUID;


public interface AbrirCuentaInputPort {

    Cuenta abrir(UUID restauranteId, String mesa);
}
