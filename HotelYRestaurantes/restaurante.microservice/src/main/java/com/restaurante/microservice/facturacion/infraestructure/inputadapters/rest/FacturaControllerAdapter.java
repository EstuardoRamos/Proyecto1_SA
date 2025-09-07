// com/restaurante/microservice/facturacion/infrastructure/inputadapters/rest/FacturaControllerAdapter.java
package com.restaurante.microservice.facturacion.infraestructure.inputadapters.rest;

import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.facturacion.application.inputports.*;
import com.restaurante.microservice.facturacion.infraestructure.inputadapters.rest.dto.EmitirFacturaRequest;
import com.restaurante.microservice.facturacion.infraestructure.inputadapters.rest.dto.FacturaResponse;
//import com.restaurante.microservice.facturacion.infrastructure.inputadapters.rest.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import java.time.Instant;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/facturas/restaurantes")
@RequiredArgsConstructor
public class FacturaControllerAdapter {

    private final EmitirFacturaInputPort emitir;
    private final ObtenerFacturaInputPort obtener;
    private final ListarFacturasInputPort listar;
    private final AnularFacturaInputPort anular; // si no lo usas, puedes quitar este bean

    @Operation(summary = "Emitir factura (desde cuenta COBRADA)")
    @PostMapping
    public FacturaResponse emitir(@RequestBody EmitirFacturaRequest req) {
        var f = emitir.emitir(
                req.cuentaId(),
                req.moneda() == null ? "GTQ" : req.moneda(),
                req.serie() == null ? "A" : req.serie(),
                req.clienteNit() == null ? "CF" : req.clienteNit(),
                req.clienteNombre() == null ? "Consumidor Final" : req.clienteNombre()
        );
        return FacturaResponse.from(f);
    }

    @Operation(summary = "Obtener factura por ID")
    @GetMapping("/{id}")
    public FacturaResponse porId(@PathVariable UUID id) {
        return FacturaResponse.from(obtener.porId(id).orElseThrow(() -> new NotFoundException("Factura no encontrada")));
    }

    @Operation(summary = "Listar facturas por restaurante y rango (devuelve solo arreglo)")
    @GetMapping
    public List<FacturaResponse> listar(@RequestParam UUID restauranteId,
            @RequestParam(required = false) Instant desde,
            @RequestParam(required = false) Instant hasta) {
        return listar.listar(restauranteId, desde, hasta).stream().map(FacturaResponse::from).toList();
    }

    @Operation(summary = "Anular factura") // opcional
    @PostMapping("/{id}/anular")
    public FacturaResponse anular(@PathVariable UUID id) {
        return FacturaResponse.from(anular.anular(id));
    }
}
