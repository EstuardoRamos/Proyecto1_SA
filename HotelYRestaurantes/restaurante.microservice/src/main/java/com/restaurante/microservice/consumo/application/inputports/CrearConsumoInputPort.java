package com.restaurante.microservice.consumo.application.inputports;

import com.restaurante.microservice.consumo.domain.ConsumoItem;
import java.util.UUID;

public interface CrearConsumoInputPort {

    ConsumoItem crear(UUID cuentaId, UUID platilloId, String nombre, java.math.BigDecimal precioUnitario, int cantidad, String nota);
}
