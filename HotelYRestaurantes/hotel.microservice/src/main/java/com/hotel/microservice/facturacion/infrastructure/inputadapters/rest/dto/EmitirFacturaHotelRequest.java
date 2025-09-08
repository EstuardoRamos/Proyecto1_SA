package com.hotel.microservice.facturacion.infrastructure.inputadapters.rest.dto;

import java.util.UUID;

public record EmitirFacturaHotelRequest(
        UUID reservaId,
        String moneda,
        String serie,
        String clienteNit,
        String clienteNombre
        ) {

}
