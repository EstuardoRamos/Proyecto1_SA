package com.hotel.microservice.facturacion.application.usecases;

import com.hotel.microservice.facturacion.application.inputports.ListarFacturasHotelInputPort;
import com.hotel.microservice.facturacion.application.outputports.persistence.FacturaHotelRepositorioPort;
import com.hotel.microservice.facturacion.domain.FacturaHotel;
import java.time.Instant;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class ListarFacturasHotelUseCase implements ListarFacturasHotelInputPort {

    private final FacturaHotelRepositorioPort facturas;

    @Override
    @Transactional
    public List<FacturaHotel> listar(UUID hotelId) {
        return facturas.listar(hotelId);
    }
}
