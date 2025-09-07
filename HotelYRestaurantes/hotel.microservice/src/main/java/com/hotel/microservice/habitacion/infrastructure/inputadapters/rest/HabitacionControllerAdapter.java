package com.hotel.microservice.habitacion.infrastructure.inputadapters.rest;

import com.hotel.microservice.habitacion.application.inputports.*;
import com.hotel.microservice.habitacion.domain.Habitacion;
import com.hotel.microservice.habitacion.infrastructure.inputadapters.rest.dto.*;
import com.hotel.microservice.common.errors.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PostMapping
    public HabitacionResponse crear(@Valid @RequestBody CrearHabitacionRequest req) {
        var h = Habitacion.nueva(req.hotelId(), req.numero(), req.tipo(), req.capacidad(), req.precioBase(), req.descripcion());
        var saved = crear.crear(h);
        return toResp(saved);
    }

    @GetMapping
    public Page<HabitacionResponse> listar(@RequestParam UUID hotelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return listar.listar(hotelId, PageRequest.of(page, size)).map(HabitacionControllerAdapter::toResp);
    }

    @GetMapping("/{id}")
    public HabitacionResponse obtener(@PathVariable UUID id) {
        var h = obtener.obtener(id).orElseThrow(() -> new NotFoundException("Habitación no encontrada"));
        return toResp(h);
    }

    @PutMapping("/{id}")
    public HabitacionResponse actualizar(@PathVariable UUID id, @Valid @RequestBody ActualizarHabitacionRequest req) {
        var cambios = new Habitacion(id, UUID.randomUUID(), // será ignorado en use case (mantiene hotelId actual)
                req.numero(), req.tipo(), req.capacidad(), req.precioBase(),
                Habitacion.Estado.DISPONIBLE, req.descripcion());
        var upd = actualizar.actualizar(id, cambios);
        return toResp(upd);
    }

    @PatchMapping("/{id}/estado")
    public HabitacionResponse cambiarEstado(@PathVariable UUID id, @RequestParam String estado) {
        var upd = cambiarEstado.cambiarEstado(id, Habitacion.Estado.valueOf(estado));
        return toResp(upd);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable UUID id) {
        eliminar.eliminar(id);
    }

    private static HabitacionResponse toResp(Habitacion h) {
        return new HabitacionResponse(h.getId(), h.getHotelId(), h.getNumero(), h.getTipo(),
                h.getCapacidad(), h.getPrecioBase(), h.getEstado().name(), h.getDescripcion());
    }
}
