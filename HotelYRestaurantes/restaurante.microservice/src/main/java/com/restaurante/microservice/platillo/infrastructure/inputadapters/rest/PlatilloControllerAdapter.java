package com.restaurante.microservice.platillo.infrastructure.inputadapters.rest;

import com.restaurante.microservice.platillo.application.inputports.*;
import com.restaurante.microservice.platillo.infrastructure.inputadapters.rest.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Platillos", description = "CRUD de platillos de restaurante")
@RestController
@RequestMapping("/v1/platillos")
@RequiredArgsConstructor
public class PlatilloControllerAdapter {

    private final CrearPlatilloInputPort crear;
    private final ActualizarPlatilloInputPort actualizar;
    private final ObtenerPlatilloInputPort obtener;
    private final DeshabilitarPlatilloInputPort deshabilitar;
    private final ListarPlatillosInputPort listar;

    private static PlatilloResponse toResponse(com.restaurante.microservice.platillo.domain.Platillo p) {
        return new PlatilloResponse(
                p.getId(), p.getRestauranteId(), p.getNombre(), p.getDescripcion(),
                p.getPrecio(), p.getImagenUrl(), p.isDisponible(), p.isEnabled(),
                p.getCreatedAt(), p.getUpdatedAt()
        );
    }

    @Operation(summary = "Crear platillo")
    @PostMapping
    public PlatilloResponse crear(@Valid @RequestBody CrearPlatilloRequest req) {
        var p = crear.crear(req.restauranteId(), req.nombre(), req.descripcion(),
                req.precio(), req.imagenUrl(), req.disponible());
        return toResponse(p);
    }

    @Operation(summary = "Obtener platillo por id")
    @GetMapping("/{id}")
    public PlatilloResponse obtener(@PathVariable UUID id) {
        return toResponse(obtener.obtener(id));
    }

    @Operation(summary = "Listar / buscar platillos")
    @GetMapping
    public PagePlatilloResponse<PlatilloResponse> listar(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) UUID restauranteId,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort
    ) {
        Pageable pageable = (sort == null || sort.isBlank())
                ? PageRequest.of(page, size)
                : PageRequest.of(page, size, Sort.by(Sort.Order.by(sort)));
        var res = listar.listar(q, restauranteId, enabled, pageable).map(PlatilloControllerAdapter::toResponse);
        return PagePlatilloResponse.from(res);
    }

    @Operation(summary = "Actualizar platillo")
    @PutMapping("/{id}")
    public PlatilloResponse actualizar(@PathVariable UUID id, @Valid @RequestBody ActualizarPlatilloRequest req) {
        var p = actualizar.actualizar(id, req.nombre(), req.descripcion(), req.precio(), req.imagenUrl(), req.disponible());
        return toResponse(p);
    }

    @Operation(summary = "Deshabilitar platillo (borrado lógico)")
    @DeleteMapping("/{id}")
    public void deshabilitar(@PathVariable UUID id) {
        deshabilitar.deshabilitar(id);
    }
}
