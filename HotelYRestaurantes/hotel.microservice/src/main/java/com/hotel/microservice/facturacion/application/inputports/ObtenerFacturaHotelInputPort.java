package com.hotel.microservice.facturacion.application.inputports;

import com.hotel.microservice.facturacion.domain.FacturaHotel;
import java.util.*;

public interface ObtenerFacturaHotelInputPort {

    Optional<FacturaHotel> porId(java.util.UUID id);
}
