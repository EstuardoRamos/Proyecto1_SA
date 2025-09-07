package com.restaurante.microservice.consumo.infrastructure.inputadapters.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CrearConsumoRequest(UUID platilloId, String nombre, BigDecimal precioUnitario, int cantidad, String nota) {

}
