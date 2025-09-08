package com.hotel.microservice.reserva.infrastructure.inputadapters.rest;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.reserva.application.inputports.*;
import com.hotel.microservice.reserva.domain.Reserva;
import com.hotel.microservice.reserva.infrastructure.inputadapters.rest.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.UUID;

@RestController
@RequestMapping("/v1/reservas")
public class ReservaControllerAdapter {

    private final CrearReservaInputPort crear;
    private final ListarReservasInputPort listar;
    private final ObtenerReservaInputPort obtener;
    private final CancelarReservaInputPort cancelar;
    private final CheckInInputPort checkIn;
    private final CheckOutInputPort checkOut;

    public ReservaControllerAdapter(CrearReservaInputPort crear, ListarReservasInputPort listar,
            ObtenerReservaInputPort obtener, CancelarReservaInputPort cancelar,
            CheckInInputPort checkIn, CheckOutInputPort checkOut) {
        this.crear = crear;
        this.listar = listar;
        this.obtener = obtener;
        this.cancelar = cancelar;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    /**
     * Crea una nueva reserva para un hotel y habitación. Requiere: hotelId,
     * habitacionId, clienteId, fechas de entrada/salida y número de huéspedes.
     */
    @Operation(
            summary = "Crear reserva",
            description = "Crea una reserva con la información del hotel, habitación, cliente y fechas.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Reserva creada",
                        content = @Content(schema = @Schema(implementation = ReservaResponse.class))),
                @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
            }
    )
    @PostMapping
    public ReservaResponse crear(@Valid @RequestBody CrearReservaRequest req) {
        var r = Reserva.nueva(req.hotelId(), req.habitacionId(), req.clienteId(),
                req.entrada(), req.salida(), req.huespedes());
        return toResp(crear.crear(r));
    }

    /**
     * Lista reservas de un hotel paginadas. Filtro obligatorio: hotelId.
     */
    @Operation(
            summary = "Listar reservas por hotel",
            description = "Devuelve una página de reservas filtradas por hotelId.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Listado paginado de reservas")
            }
    )
    @GetMapping
    public Page<ReservaResponse> listar(
            @Parameter(description = "ID del hotel") @RequestParam UUID hotelId,
            @Parameter(description = "Página (0..N)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "20") int size) {
        return listar.listar(hotelId, PageRequest.of(page, size)).map(ReservaControllerAdapter::toResp);
    }

    /**
     * Obtiene una reserva por su ID.
     */
    @Operation(
            summary = "Obtener reserva",
            description = "Recupera una reserva por su identificador.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Reserva encontrada",
                        content = @Content(schema = @Schema(implementation = ReservaResponse.class))),
                @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ReservaResponse obtener(@Parameter(description = "ID de la reserva") @PathVariable UUID id) {
        var r = obtener.obtener(id).orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
        return toResp(r);
    }

    /**
     * Cancela una reserva (cambio de estado a CANCELADA).
     */
    @Operation(
            summary = "Cancelar reserva",
            description = "Marca la reserva como CANCELADA.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Reserva cancelada"),
                @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content)
            }
    )
    @PatchMapping("/{id}/cancelar")
    public void cancelar(@Parameter(description = "ID de la reserva") @PathVariable UUID id) {
        cancelar.cancelar(id);
    }

    /**
     * Realiza el check-in de la reserva (transición de estado).
     */
    @Operation(
            summary = "Check-in de reserva",
            description = "Realiza el check-in de la reserva si la transición de estado es válida.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Check-in OK"),
                @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content),
                @ApiResponse(responseCode = "409", description = "Transición inválida", content = @Content)
            }
    )
    @PostMapping("/{id}/checkin")
    public void checkIn(@Parameter(description = "ID de la reserva") @PathVariable UUID id) {
        checkIn.checkIn(id);
    }

    /**
     * Realiza el check-out de la reserva (transición de estado). Puede disparar
     * facturación.
     */
    @Operation(
            summary = "Check-out de reserva",
            description = "Realiza el check-out de la reserva si la transición de estado es válida. "
            + "Dependiendo de la orquestación, puede emitir la factura.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Check-out OK"),
                @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content),
                @ApiResponse(responseCode = "409", description = "Transición inválida", content = @Content)
            }
    )
    @PostMapping("/{id}/checkout")
    public void checkOut(@Parameter(description = "ID de la reserva") @PathVariable UUID id) {
        checkOut.checkOut(id);
    }

    private static ReservaResponse toResp(Reserva r) {
        return new ReservaResponse(r.getId(), r.getHotelId(), r.getHabitacionId(), r.getClienteId(),
                r.getEntrada(), r.getSalida(), r.getHuespedes(), r.getEstado().name(), r.getTotal());
    }
}
