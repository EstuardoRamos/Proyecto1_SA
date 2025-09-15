package com.hotel.microservice.facturacion.infrastructure.inputadapters.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.facturacion.application.inputports.AnularFacturaHotelInputPort;
import com.hotel.microservice.facturacion.application.inputports.EmitirFacturaHotelInputPort;
import com.hotel.microservice.facturacion.application.inputports.ListarFacturasHotelInputPort;
import com.hotel.microservice.facturacion.application.inputports.ObtenerFacturaHotelInputPort;
import com.hotel.microservice.facturacion.infrastructure.inputadapters.rest.dto.EmitirFacturaHotelRequest;
import com.hotel.microservice.facturacion.infrastructure.inputadapters.rest.dto.FacturaHotelResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

//@CrossOrigin(origins = "http://frontend-comerdormir.s3-website.us-east-2.amazonaws.com", allowCredentials = "true")
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
    public java.util.List<FacturaHotelResponse> listar(@RequestParam UUID hotelId) {
        return listar.listar(hotelId).stream().map(FacturaHotelResponse::from).toList();
    }

    @Operation(summary = "Anular factura de hotel") // opcional
    @PostMapping("/{id}/anular")
    public FacturaHotelResponse anular(@PathVariable UUID id) {
        return FacturaHotelResponse.from(anular.anular(id));
    }
}
