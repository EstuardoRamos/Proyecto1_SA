package com.hotel.microservice.facturacion.application.inputports;

import com.hotel.microservice.facturacion.domain.FacturaHotel;
import java.time.Instant;
import java.util.*;

public interface ListarFacturasHotelInputPort {

    java.util.List<FacturaHotel> listar(java.util.UUID hotelId, Instant desde, Instant hasta);
}
