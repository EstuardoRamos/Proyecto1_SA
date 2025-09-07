package com.restaurante.microservice.restaurante.application.inputoports;


import com.restaurante.microservice.restaurante.domain.Restaurante;
import java.math.BigDecimal;
import java.util.UUID;

public interface CrearRestauranteInputPort {
  Restaurante crear(UUID hotelId, String nombre, String direccion,
                    BigDecimal impuestoPorc, BigDecimal propinaPorcDefault);
}