package com.restaurante.microservice.restaurante.application.inputoports;


import com.restaurante.microservice.restaurante.domain.Restaurante;
import java.math.BigDecimal;
import java.util.UUID;

public interface ActualizarRestauranteInputPort {
  Restaurante actualizar(UUID id, String nombre, String direccion,
                         BigDecimal impuestoPorc, BigDecimal propinaPorcDefault);
}