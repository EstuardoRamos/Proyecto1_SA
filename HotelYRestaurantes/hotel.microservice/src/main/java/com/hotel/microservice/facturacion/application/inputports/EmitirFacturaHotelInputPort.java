package com.hotel.microservice.facturacion.application.inputports;

import com.hotel.microservice.facturacion.domain.FacturaHotel;
import java.util.UUID;

public interface EmitirFacturaHotelInputPort {

    FacturaHotel emitir(UUID reservaId, String moneda, String serie, String clienteNit, String clienteNombre);
}
