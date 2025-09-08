package com.hotel.microservice.facturacion.infrastructure.inputadapters.rest;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.facturacion.application.inputports.*;
import com.hotel.microservice.facturacion.infrastructure.inputadapters.rest.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import java.time.Instant;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/facturas/hotel")
@RequiredArgsConstructor
public class FacturaHotelControllerAdapter {

    private final EmitirFacturaHotelInputPort emitir;
    private final ObtenerFacturaHotelInputPort obtener;
    private final ListarFacturasHotelInputPort listar;
    private final AnularFacturaHotelInputPort anular; // opcional

    @Operation(summary = "Emitir factura de hotel (desde reserva finalizada)")
    @PostMapping
    public FacturaHotelResponse emitir(@RequestBody EmitirFacturaHotelRequest req) {
        var f = emitir.emitir(
                req.reservaId(),
                req.moneda() == null ? "GTQ" : req.moneda(),
                req.serie() == null ? "A" : req.serie(),
                req.clienteNit(), req.clienteNombre()
        );
        return FacturaHotelResponse.from(f);
    }

    @Operation(summary = "Obtener factura de hotel por ID")
    @GetMapping("/{id}")
    public FacturaHotelResponse porId(@PathVariable UUID id) {
        return FacturaHotelResponse.from(
                obtener.porId(id).orElseThrow(() -> new NotFoundException("Factura no encontrada")));
    }

    @Operation(summary = "Listar facturas de hotel por hotel y rango (devuelve solo arreglo)")
    @GetMapping
    public java.util.List<FacturaHotelResponse> listar(@RequestParam UUID hotelId,
            @RequestParam(required = false) Instant desde,
            @RequestParam(required = false) Instant hasta) {
        return listar.listar(hotelId, desde, hasta).stream().map(FacturaHotelResponse::from).toList();
    }

    @Operation(summary = "Anular factura de hotel") // opcional
    @PostMapping("/{id}/anular")
    public FacturaHotelResponse anular(@PathVariable UUID id) {
        return FacturaHotelResponse.from(anular.anular(id));
    }
}
