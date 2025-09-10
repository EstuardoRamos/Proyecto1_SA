package com.restaurante.microservice.platillo.infrastructure.inputadapters.rest;

import com.restaurante.microservice.platillo.application.inputports.*;
import com.restaurante.microservice.platillo.infrastructure.inputadapters.rest.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")

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
    public List<PlatilloResponse> listar(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) UUID restauranteId,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort
    ) {
        // soporta sort asc y "-campo" para desc
        Sort sortSpec = Sort.unsorted();
        if (sort != null && !sort.isBlank()) {
            sortSpec = sort.startsWith("-")
                    ? Sort.by(Sort.Order.desc(sort.substring(1)))
                    : Sort.by(Sort.Order.asc(sort));
        }

        var pageable = PageRequest.of(page, size, sortSpec);

        return listar.listar(q, restauranteId, enabled, pageable)
                .map(PlatilloControllerAdapter::toResponse) // Page<PlatilloResponse>
                .getContent();                               // List<PlatilloResponse>
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
