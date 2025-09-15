package com.hotel.microservice.habitacion.infrastructure.inputadapters.rest;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.habitacion.application.inputports.ActualizarHabitacionInputPort;
import com.hotel.microservice.habitacion.application.inputports.CambiarEstadoHabitacionInputPort;
import com.hotel.microservice.habitacion.application.inputports.CrearHabitacionInputPort;
import com.hotel.microservice.habitacion.application.inputports.EliminarHabitacionInputPort;
import com.hotel.microservice.habitacion.application.inputports.ListarHabitacionesInputPort;
import com.hotel.microservice.habitacion.application.inputports.ObtenerHabitacionInputPort;
import com.hotel.microservice.habitacion.domain.Habitacion;
import com.hotel.microservice.habitacion.infrastructure.inputadapters.rest.dto.ActualizarHabitacionRequest;
import com.hotel.microservice.habitacion.infrastructure.inputadapters.rest.dto.CrearHabitacionRequest;
import com.hotel.microservice.habitacion.infrastructure.inputadapters.rest.dto.HabitacionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://frontend-comerdormir.s3-website.us-east-2.amazonaws.com", allowCredentials = "true")
@RestController
@RequestMapping("/v1/habitaciones")
public class HabitacionControllerAdapter {

    private final CrearHabitacionInputPort crear;
    private final ListarHabitacionesInputPort listar;
    private final ObtenerHabitacionInputPort obtener;
    private final ActualizarHabitacionInputPort actualizar;
    private final EliminarHabitacionInputPort eliminar;
    private final CambiarEstadoHabitacionInputPort cambiarEstado;

    public HabitacionControllerAdapter(
            CrearHabitacionInputPort crear,
            ListarHabitacionesInputPort listar,
            ObtenerHabitacionInputPort obtener,
            ActualizarHabitacionInputPort actualizar,
            EliminarHabitacionInputPort eliminar,
            CambiarEstadoHabitacionInputPort cambiarEstado) {
        this.crear = crear;
        this.listar = listar;
        this.obtener = obtener;
        this.actualizar = actualizar;
        this.eliminar = eliminar;
        this.cambiarEstado = cambiarEstado;
    }

    /**
     * Crea una nueva habitación para un hotel. Requiere: hotelId, número, tipo,
     * capacidad, precioBase y (opcional) descripción. Retorna la habitación
     * creada.
     */
    @Operation(
            summary = "Crear habitación",
            description = "Crea una nueva habitación asociada a un hotel.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Habitación creada",
                        content = @Content(schema = @Schema(implementation = HabitacionResponse.class))),
                @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
            }
    )
    @PostMapping
    public HabitacionResponse crear(@Valid @RequestBody CrearHabitacionRequest req) {
        var h = Habitacion.nueva(req.hotelId(), req.numero(), req.tipo(), req.capacidad(), req.precioBase(), req.descripcion());
        var saved = crear.crear(h);
        return toResp(saved);
    }

    /**
     * Lista habitaciones de un hotel paginadas. Filtro obligatorio: hotelId.
     * Parámetros de paginación: page, size.
     */
    @Operation(
            summary = "Listar habitaciones por hotel",
            description = "Devuelve una página de habitaciones del hotel indicado por hotelId.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Listado paginado de habitaciones")
            }
    )
    @GetMapping
    public Page<HabitacionResponse> listar(
            @Parameter(description = "ID del hotel") @RequestParam UUID hotelId,
            @Parameter(description = "Página (0..N)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "20") int size) {
        return listar.listar(hotelId, PageRequest.of(page, size)).map(HabitacionControllerAdapter::toResp);
    }

    /**
     * Obtiene una habitación por su ID. Lanza NotFound si no existe.
     */
    @Operation(
            summary = "Obtener habitación",
            description = "Recupera una habitación por su identificador.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Habitación encontrada",
                        content = @Content(schema = @Schema(implementation = HabitacionResponse.class))),
                @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public HabitacionResponse obtener(@Parameter(description = "ID de la habitación") @PathVariable UUID id) {
        var h = obtener.obtener(id).orElseThrow(() -> new NotFoundException("Habitación no encontrada"));
        return toResp(h);
    }

    /**
     * Actualiza datos básicos de una habitación (número, tipo, capacidad,
     * precioBase, descripción). El hotelId existente se mantiene (el valor
     * enviado se ignora en el use case).
     */
    @Operation(
            summary = "Actualizar habitación",
            description = "Actualiza los campos editables de una habitación existente.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Habitación actualizada",
                        content = @Content(schema = @Schema(implementation = HabitacionResponse.class))),
                @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public HabitacionResponse actualizar(
            @Parameter(description = "ID de la habitación") @PathVariable UUID id,
            @Valid @RequestBody ActualizarHabitacionRequest req) {
        var cambios = new Habitacion(id, UUID.randomUUID(), // ignorado por el caso de uso, conserva hotelId actual
                req.numero(), req.tipo(), req.capacidad(), req.precioBase(),
                Habitacion.Estado.DISPONIBLE, req.descripcion());
        var upd = actualizar.actualizar(id, cambios);
        return toResp(upd);
    }

    /**
     * Cambia el estado de la habitación (e.g., DISPONIBLE, OCUPADA,
     * MANTENIMIENTO).
     */
    @Operation(
            summary = "Cambiar estado de la habitación",
            description = "Cambia el estado de una habitación. Valores válidos: DISPONIBLE, OCUPADA, MANTENIMIENTO, etc.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Estado actualizado",
                        content = @Content(schema = @Schema(implementation = HabitacionResponse.class))),
                @ApiResponse(responseCode = "400", description = "Estado inválido", content = @Content),
                @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content)
            }
    )
    @PatchMapping("/{id}/estado")
    public HabitacionResponse cambiarEstado(
            @Parameter(description = "ID de la habitación") @PathVariable UUID id,
            @Parameter(description = "Nuevo estado (enum Habitacion.Estado)") @RequestParam String estado) {
        var upd = cambiarEstado.cambiarEstado(id, Habitacion.Estado.valueOf(estado));
        return toResp(upd);
    }

    /**
     * Elimina (hard-delete o soft-delete según tu capa de aplicación) una
     * habitación.
     */
    @Operation(
            summary = "Eliminar habitación",
            description = "Elimina la habitación por ID.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Eliminada"),
                @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public void eliminar(@Parameter(description = "ID de la habitación") @PathVariable UUID id) {
        eliminar.eliminar(id);
    }

    private static HabitacionResponse toResp(Habitacion h) {
        return new HabitacionResponse(h.getId(), h.getHotelId(), h.getNumero(), h.getTipo(),
                h.getCapacidad(), h.getPrecioBase(), h.getEstado().name(), h.getDescripcion());
    }
}
