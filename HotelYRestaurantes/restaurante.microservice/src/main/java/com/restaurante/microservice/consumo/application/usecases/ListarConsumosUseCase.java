package com.restaurante.microservice.consumo.application.usecases;

import com.restaurante.microservice.consumo.application.inputports.ListarConsumosInputPort;
import com.restaurante.microservice.consumo.application.outputports.persistence.ConsumoRepositorioPort;
import com.restaurante.microservice.consumo.domain.ConsumoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class ListarConsumosUseCase implements ListarConsumosInputPort {

    private final ConsumoRepositorioPort consumos;

    public ListarConsumosUseCase(ConsumoRepositorioPort consumos) {
        this.consumos = consumos;
    }

    @Override
    public Page<ConsumoItem> porCuenta(UUID cuentaId, Pageable pageable) {
        return consumos.listarPorCuenta(cuentaId, pageable);
    }
}
