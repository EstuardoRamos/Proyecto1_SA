// ingresos/application/usecases/GenerarReporteIngresosUseCase.java
package com.reportes.microservice.ingresos.application.usecases;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.reportes.microservice.ingresos.application.inputports.GenerarReporteIngresosInputPort;
import com.reportes.microservice.ingresos.application.outputports.query.FacturacionHotelQueryPort;
import com.reportes.microservice.ingresos.application.outputports.query.FacturacionRestQueryPort;

public class GenerarReporteIngresosUseCase implements GenerarReporteIngresosInputPort {

    private final FacturacionHotelQueryPort hotel;
    private final FacturacionRestQueryPort rest;

    public GenerarReporteIngresosUseCase(FacturacionHotelQueryPort hotel, FacturacionRestQueryPort rest) {
        this.hotel = hotel;
        this.rest = rest;
    }

    @Override
    public ResultadoHotel ingresosHotel(UUID hotelId, Instant desde, Instant hasta) {
        List<FacturacionHotelQueryPort.FacturaSnapshot> facturas = hotel.listar(hotelId, desde, hasta);
        var total = facturas.stream().map(FacturacionHotelQueryPort.FacturaSnapshot::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new ResultadoHotel(hotelId, desde, hasta, total, facturas);
    }

    @Override
    public ResultadoRest ingresosRestaurante(UUID restauranteId, Instant desde, Instant hasta) {
        List<FacturacionRestQueryPort.FacturaSnapshot> facturas = rest.listar(restauranteId, desde, hasta);
        var total = facturas.stream().map(FacturacionRestQueryPort.FacturaSnapshot::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new ResultadoRest(restauranteId, desde, hasta, total, facturas);
    }
}
