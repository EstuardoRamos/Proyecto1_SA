package com.hotel.microservice.facturacion.application.outputports.persistence;

import com.hotel.microservice.facturacion.domain.FacturaHotel;
import java.time.Instant;
import java.util.*;

public interface FacturaHotelRepositorioPort {

    FacturaHotel guardar(FacturaHotel f);

    Optional<FacturaHotel> porId(java.util.UUID id);

    Optional<FacturaHotel> porReservaId(java.util.UUID reservaId);     // idempotencia

    java.util.List<FacturaHotel> listar(java.util.UUID hotelId, Instant desde, Instant hasta);

    int maxNumeroEnSerie(java.util.UUID hotelId, String serie);
}
