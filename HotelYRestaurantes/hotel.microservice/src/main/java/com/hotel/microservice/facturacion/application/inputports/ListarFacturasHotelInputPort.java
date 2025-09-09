package com.hotel.microservice.facturacion.application.inputports;

import com.hotel.microservice.facturacion.domain.FacturaHotel;
import java.time.Instant;
import java.util.*;

public interface ListarFacturasHotelInputPort {

    List<FacturaHotel> listar(UUID hotelId);
}
