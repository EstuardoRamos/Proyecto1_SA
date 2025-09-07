package com.restaurante.microservice.cuenta.infrastructure.inputadapters.rest.dto;


import java.util.UUID;

public record AbrirCuentaRequest(UUID restauranteId, String mesa) {}