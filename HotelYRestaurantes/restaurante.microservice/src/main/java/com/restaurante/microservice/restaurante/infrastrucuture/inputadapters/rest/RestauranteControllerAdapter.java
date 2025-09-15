package com.restaurante.microservice.restaurante.infrastrucuture.inputadapters.rest;

import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.restaurante.application.inputoports.*;
import com.restaurante.microservice.restaurante.domain.Restaurante;
import com.restaurante.microservice.restaurante.infrastrucuture.inputadapters.rest.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://frontend-comerdormir.s3-website.us-east-2.amazonaws.com", allowCredentials = "true")
@RestController
@RequestMapping("/v1/restaurantes")
public class RestauranteControllerAdapter {

  private final CrearRestauranteInputPort crear;
  private final ActualizarRestauranteInputPort actualizar;
  private final DeshabilitarRestauranteInputPort deshabilitar;
  private final HabilitarRestauranteInputPort habilitar;
  private final ObtenerRestauranteInputPort obtener;
  private final ListarRestaurantesInputPort listar;
  private final VincularHotelInputPort vincular;
  private final DesvincularHotelInputPort desvincular;

  public RestauranteControllerAdapter(CrearRestauranteInputPort crear,
                                      ActualizarRestauranteInputPort actualizar,
                                      DeshabilitarRestauranteInputPort deshabilitar,
                                      HabilitarRestauranteInputPort habilitar,
                                      ObtenerRestauranteInputPort obtener,
                                      ListarRestaurantesInputPort listar,
                                      VincularHotelInputPort vincular,
                                      DesvincularHotelInputPort desvincular) {
    this.crear = crear; this.actualizar = actualizar; this.deshabilitar = deshabilitar; this.habilitar=habilitar;
    this.obtener = obtener; this.listar = listar; this.vincular = vincular; this.desvincular = desvincular;
  }

  @Operation(
      summary = "Crear restaurante",
      description = "Crea un nuevo restaurante. El `hotelId` es opcional (puede ser `null`)."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Restaurante creado"),
      @ApiResponse(responseCode = "400", description = "Datos inválidos"),
      @ApiResponse(responseCode = "409", description = "Conflicto por nombre duplicado en el mismo hotel")
  })
  @PostMapping
  public RestauranteResponse crear(@Valid @RequestBody CrearRestauranteRequest req) {
    var r = crear.crear(req.hotelId(), req.nombre(), req.direccion(), req.impuestoPorc(), req.propinaPorcDefault());
    return toResp(r);
  }

  @Operation(
      summary = "Obtener restaurante por ID",
      description = "Devuelve los datos de un restaurante específico."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Encontrado"),
      @ApiResponse(responseCode = "404", description = "No existe el restaurante")
  })
  @GetMapping("/{id}")
  public RestauranteResponse obtener(@PathVariable UUID id) {
    var r = obtener.obtener(id).orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));
    return toResp(r);
  }

  @Operation(
      summary = "Actualizar restaurante",
      description = "Modifica nombre, dirección e impuestos/propina por defecto de un restaurante."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Actualizado"),
      @ApiResponse(responseCode = "400", description = "Datos inválidos"),
      @ApiResponse(responseCode = "404", description = "No existe el restaurante"),
      @ApiResponse(responseCode = "409", description = "Conflicto por nombre duplicado en el mismo hotel")
  })
  @PutMapping("/{id}")
  public RestauranteResponse actualizar(@PathVariable UUID id,
                                        @Valid @RequestBody ActualizarRestauranteRequest req) {
    var r = actualizar.actualizar(id, req.nombre(), req.direccion(), req.impuestoPorc(), req.propinaPorcDefault());
    return toResp(r);
  }

  @Operation(
      summary = "Deshabilitar restaurante",
      description = "Marca el restaurante como inactivo (soft delete)."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Deshabilitado"),
      @ApiResponse(responseCode = "404", description = "No existe el restaurante")
  })
  @DeleteMapping("/{id}")
  public void deshabilitar(@PathVariable UUID id) { deshabilitar.deshabilitar(id); }

  @Operation(
      summary = "Listar restaurantes",
      description = "Lista restaurantes como **arreglo** (no página). Admite filtros por texto (`q`), hotel (`hotelId`) y estado (`enabled`). Soporta paginación con `page`, `size`, `sort` vía `Pageable`."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Listado devuelto"),
      @ApiResponse(responseCode = "400", description = "`hotelId` inválido")
  })
  @GetMapping
  public List<RestauranteResponse> listar(
      @Parameter(description = "Texto a buscar en nombre/dirección")
      @RequestParam(required = false) String q,
      @Parameter(description = "Filtrar por hotel (UUID). Puede omitirse.")
      @RequestParam(required = false, name = "hotelId") String hotelIdStr,
      @Parameter(description = "Filtrar por habilitado (true) o deshabilitado (false)")
      @RequestParam(required = false) Boolean enabled,
      @ParameterObject Pageable pageable
  ) {
    UUID hotelId = parseUuidOrNull(hotelIdStr);
    return listar.listar(q, hotelId, enabled, pageable)
                 .map(RestauranteControllerAdapter::toResp)
                 .getContent();
  }

  @Operation(
      summary = "Vincular restaurante a hotel",
      description = "Asocia el restaurante a un `hotelId` (o lo cambia de hotel)."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Vinculado"),
      @ApiResponse(responseCode = "404", description = "No existe el restaurante")
  })
  @PatchMapping("/{id}/hotel")
  public RestauranteResponse vincularHotel(@PathVariable UUID id, @Valid @RequestBody VincularHotelRequest req) {
    return toResp(vincular.vincular(id, req.getHotelId()));
  }

  @Operation(
  summary = "Habilitar restaurante",
  description = "Vuelve a activar (enabled=true) un restaurante previamente deshabilitado."
)
@ApiResponse(responseCode = "200", description = "Restaurante habilitado")
@ApiResponse(responseCode = "404", description = "No existe el restaurante")
@PatchMapping("/{id}/habilitar") // o POST si prefieres
public RestauranteResponse habilitar(@PathVariable UUID id) {
  return toResp(habilitar.habilitar(id));
}

  @Operation(
      summary = "Desvincular restaurante de hotel",
      description = "Quita la asociación con el hotel (deja `hotelId` en `null`)."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Desvinculado"),
      @ApiResponse(responseCode = "404", description = "No existe el restaurante")
  })
  @DeleteMapping("/{id}/hotel")
  public RestauranteResponse desvincularHotel(@PathVariable UUID id) {
    return toResp(desvincular.desvincular(id));
  }

  private static UUID parseUuidOrNull(String s) {
    if (s == null || s.isBlank() || "null".equalsIgnoreCase(s)) return null;
    try {
      return UUID.fromString(s.trim());
    } catch (IllegalArgumentException ex) {
      throw new org.springframework.web.server.ResponseStatusException(
          org.springframework.http.HttpStatus.BAD_REQUEST,
          "hotelId inválido: " + s
      );
    }
  }

  private static RestauranteResponse toResp(Restaurante r){
    return new RestauranteResponse(
        r.getId(), r.getHotelId(), r.getNombre(), r.getDireccion(),
        r.getImpuestoPorc(), r.getPropinaPorcDefault(), r.isEnabled()
    );
  }
}