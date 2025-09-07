package com.restaurante.microservice.consumo.application.inputports;

import com.restaurante.microservice.consumo.domain.ConsumoItem;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarConsumosInputPort {

    Page<ConsumoItem> porCuenta(UUID cuentaId, Pageable pageable);
}
