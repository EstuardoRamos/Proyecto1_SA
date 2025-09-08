// application/usecases/AnularFacturaHotelUseCase.java
package com.hotel.microservice.facturacion.application.usecases;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.facturacion.application.inputports.AnularFacturaHotelInputPort;
import com.hotel.microservice.facturacion.application.outputports.persistence.FacturaHotelRepositorioPort;
import com.hotel.microservice.facturacion.domain.FacturaHotel;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AnularFacturaHotelUseCase implements AnularFacturaHotelInputPort {

    private final FacturaHotelRepositorioPort facturas;

    @Override
    public FacturaHotel anular(UUID facturaId) {
        var f = facturas.porId(facturaId).orElseThrow(() -> new NotFoundException("Factura no encontrada"));
        return facturas.guardar(f.anular());
    }
}
