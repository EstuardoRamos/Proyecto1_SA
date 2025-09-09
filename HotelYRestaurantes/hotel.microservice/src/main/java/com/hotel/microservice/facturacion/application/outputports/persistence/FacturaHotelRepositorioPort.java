package com.hotel.microservice.facturacion.application.outputports.persistence;

import com.hotel.microservice.facturacion.domain.FacturaHotel;
import java.time.Instant;
import java.util.*;

public interface FacturaHotelRepositorioPort {

    FacturaHotel guardar(FacturaHotel f);

    Optional<FacturaHotel> porId(UUID id);

    Optional<FacturaHotel> porReservaId(UUID reservaId);     // idempotencia

    List<FacturaHotel> listar(UUID hotelId);

    int maxNumeroEnSerie(UUID hotelId, String serie);
}
