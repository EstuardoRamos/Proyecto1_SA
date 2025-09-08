// application/usecases/ObtenerFacturaHotelUseCase.java
package com.hotel.microservice.facturacion.application.usecases;

import com.hotel.microservice.facturacion.application.inputports.ObtenerFacturaHotelInputPort;
import com.hotel.microservice.facturacion.application.outputports.persistence.FacturaHotelRepositorioPort;
import com.hotel.microservice.facturacion.domain.FacturaHotel;
import java.util.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObtenerFacturaHotelUseCase implements ObtenerFacturaHotelInputPort {

    private final FacturaHotelRepositorioPort facturas;

    @Override
    public Optional<FacturaHotel> porId(UUID id) {
        return facturas.porId(id);
    }
}
