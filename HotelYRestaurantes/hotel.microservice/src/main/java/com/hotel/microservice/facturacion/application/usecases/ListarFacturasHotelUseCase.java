package com.hotel.microservice.facturacion.application.usecases;

import com.hotel.microservice.facturacion.application.inputports.ListarFacturasHotelInputPort;
import com.hotel.microservice.facturacion.application.outputports.persistence.FacturaHotelRepositorioPort;
import com.hotel.microservice.facturacion.domain.FacturaHotel;
import java.time.Instant;
import java.util.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListarFacturasHotelUseCase implements ListarFacturasHotelInputPort {

    private final FacturaHotelRepositorioPort facturas;

    @Override
    public java.util.List<FacturaHotel> listar(UUID hotelId, Instant desde, Instant hasta) {
        return facturas.listar(hotelId, desde, hasta);
    }
}
