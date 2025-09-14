// ingresos/infrastructure/inputadapters/rest/ReporteIngresosControllerAdapter.java
package com.reportes.microservice.ingresos.infrastructure.inputadapters.rest;

import com.reportes.microservice.ingresos.application.inputports.GenerarReporteIngresosInputPort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.UUID;

@RestController
@RequestMapping("/v1/reportes/ingresos")
public class ReporteIngresosControllerAdapter {

    private final GenerarReporteIngresosInputPort usecase;

    public ReporteIngresosControllerAdapter(GenerarReporteIngresosInputPort usecase) {
        this.usecase = usecase;
    }

    @GetMapping("/hotel")
    public GenerarReporteIngresosInputPort.ResultadoHotel ingresosHotel(
            @RequestParam UUID hotelId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        var range = toInstants(desde, hasta);
        return usecase.ingresosHotel(hotelId, range.desde(), range.hasta());
    }

    @GetMapping("/restaurante")
    public GenerarReporteIngresosInputPort.ResultadoRest ingresosRest(
            @RequestParam UUID restauranteId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        var range = toInstants(desde, hasta);
        return usecase.ingresosRestaurante(restauranteId, range.desde(), range.hasta());
    }

    // Helpers: convierte LocalDate -> Instant en UTC (inicio/fin de día)
    private record Range(Instant desde, Instant hasta) {

    }

    private static Range toInstants(LocalDate d, LocalDate h) {
        if (d == null && h == null) {
            return new Range(null, null);
        }
        var zone = ZoneOffset.UTC;
        Instant di = (d == null) ? null : d.atStartOfDay(zone).toInstant();
        Instant hi = (h == null) ? null : h.plusDays(1).atStartOfDay(zone).toInstant().minusNanos(1);
        return new Range(di, hi);
    }
}
