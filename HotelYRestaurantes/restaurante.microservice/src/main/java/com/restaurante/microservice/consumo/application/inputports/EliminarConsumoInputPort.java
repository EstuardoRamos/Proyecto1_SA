package com.restaurante.microservice.consumo.application.inputports;


import java.util.UUID;

public interface EliminarConsumoInputPort {

    void eliminar(UUID id);
}
