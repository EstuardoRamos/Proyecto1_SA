package com.hotel.microservice.facturacion.application.inputports;

import com.hotel.microservice.facturacion.domain.FacturaHotel;
import java.util.UUID;

public interface AnularFacturaHotelInputPort {

    FacturaHotel anular(UUID facturaId);
}
