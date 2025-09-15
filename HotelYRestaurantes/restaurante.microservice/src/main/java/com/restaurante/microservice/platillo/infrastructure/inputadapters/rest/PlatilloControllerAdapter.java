package com.restaurante.microservice.platillo.infrastructure.inputadapters.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.microservice.platillo.application.inputports.ActualizarPlatilloInputPort;
import com.restaurante.microservice.platillo.application.inputports.CrearPlatilloInputPort;
import com.restaurante.microservice.platillo.application.inputports.DeshabilitarPlatilloInputPort;
import com.restaurante.microservice.platillo.application.inputports.ListarPlatillosInputPort;
import com.restaurante.microservice.platillo.application.inputports.ObtenerPlatilloInputPort;
import com.restaurante.microservice.platillo.infrastructure.inputadapters.rest.dto.ActualizarPlatilloRequest;
import com.restaurante.microservice.platillo.infrastructure.inputadapters.rest.dto.CrearPlatilloRequest;
import com.restaurante.microservice.platillo.infrastructure.inputadapters.rest.dto.PlatilloResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@CrossOrigin(origins = "http://frontend-comerdormir.s3-website.us-east-2.amazonaws.com", allowCredentials = "true")

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
