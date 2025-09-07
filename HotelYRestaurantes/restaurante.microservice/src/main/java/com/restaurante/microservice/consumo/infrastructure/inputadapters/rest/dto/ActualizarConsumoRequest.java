package com.restaurante.microservice.consumo.infrastructure.inputadapters.rest.dto;

import java.math.BigDecimal;

public record ActualizarConsumoRequest(BigDecimal precioUnitario, Integer cantidad, String nota) {

}
