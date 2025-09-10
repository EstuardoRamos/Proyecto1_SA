// application/inputoports/HabilitarRestauranteInputPort.java
package com.restaurante.microservice.restaurante.application.inputoports;

import com.restaurante.microservice.restaurante.domain.Restaurante;
import java.util.UUID;

public interface HabilitarRestauranteInputPort {
  Restaurante habilitar(UUID id);
}