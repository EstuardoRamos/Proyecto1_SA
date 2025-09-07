package com.restaurante.microservice.restaurante.infrastrucuture.inputadapters.rest;

//import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.restaurante.application.inputoports.ActualizarRestauranteInputPort;
import com.restaurante.microservice.restaurante.application.inputoports.CrearRestauranteInputPort;
import com.restaurante.microservice.restaurante.application.inputoports.DeshabilitarRestauranteInputPort;
import com.restaurante.microservice.restaurante.application.inputoports.DesvincularHotelInputPort;
import com.restaurante.microservice.restaurante.application.inputoports.ListarRestaurantesInputPort;
import com.restaurante.microservice.restaurante.application.inputoports.ObtenerRestauranteInputPort;
import com.restaurante.microservice.restaurante.application.inputoports.VincularHotelInputPort;
import com.restaurante.microservice.restaurante.domain.Restaurante;
import com.restaurante.microservice.restaurante.infrastrucuture.inputadapters.rest.dto.ActualizarRestauranteRequest;
import com.restaurante.microservice.restaurante.infrastrucuture.inputadapters.rest.dto.CrearRestauranteRequest;
import com.restaurante.microservice.restaurante.infrastrucuture.inputadapters.rest.dto.RestauranteResponse;
import com.restaurante.microservice.restaurante.infrastrucuture.inputadapters.rest.dto.VincularHotelRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/v1/restaurantes")
public class RestauranteControllerAdapter {

  private final CrearRestauranteInputPort crear;
  private final ActualizarRestauranteInputPort actualizar;
  private final DeshabilitarRestauranteInputPort deshabilitar;
  private final ObtenerRestauranteInputPort obtener;
  private final ListarRestaurantesInputPort listar;
  private final VincularHotelInputPort vincular;
  private final DesvincularHotelInputPort desvincular;

  public RestauranteControllerAdapter(CrearRestauranteInputPort crear,
                                      ActualizarRestauranteInputPort actualizar,
                                      DeshabilitarRestauranteInputPort deshabilitar,
                                      ObtenerRestauranteInputPort obtener,
                                      ListarRestaurantesInputPort listar,
                                      VincularHotelInputPort vincular,
                                      DesvincularHotelInputPort desvincular) {
    this.crear = crear; this.actualizar = actualizar; this.deshabilitar = deshabilitar;
    this.obtener = obtener; this.listar = listar; this.vincular = vincular; this.desvincular = desvincular;
  }
  
  
  @Operation(
    summary = "Crear restaurante",
    description = "Crea un nuevo restaurante. El `hotelId` es opcional (puede ser `null`)."
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Restaurante creado"),
    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
    @ApiResponse(responseCode = "409", description = "Ya existe un restaurante con ese nombre en el mismo hotel")
  })

  @PostMapping
  public RestauranteResponse crear(@Valid @RequestBody CrearRestauranteRequest req) {
    var r = crear.crear(req.hotelId(), req.nombre(), req.direccion(), req.impuestoPorc(), req.propinaPorcDefault());
    return toResp(r);
  }

  @GetMapping("/{id}")
  public RestauranteResponse obtener(@PathVariable UUID id) {
    var r = obtener.obtener(id).orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));
    return toResp(r);
  }

  @PutMapping("/{id}")
  public RestauranteResponse actualizar(@PathVariable UUID id,
                                        @Valid @RequestBody ActualizarRestauranteRequest req) {
    var r = actualizar.actualizar(id, req.nombre(), req.direccion(), req.impuestoPorc(), req.propinaPorcDefault());
    return toResp(r);
  }

  @DeleteMapping("/{id}")
  public void deshabilitar(@PathVariable UUID id) { deshabilitar.deshabilitar(id); }

@GetMapping
public List<RestauranteResponse> listar(
    @RequestParam(required = false) String q,
    @RequestParam(required = false, name = "hotelId") String hotelIdStr,
    @RequestParam(required = false) Boolean enabled,
    @ParameterObject org.springframework.data.domain.Pageable pageable
) {
  UUID hotelId = parseUuidOrNull(hotelIdStr);

  return listar.listar(q, hotelId, enabled, pageable)   // Page<Restaurante>
               .map(RestauranteControllerAdapter::toResp) // Page<RestauranteResponse>
               .getContent();                             // List<RestauranteResponse>
}

private static UUID parseUuidOrNull(String s) {
  if (s == null || s.isBlank() || "null".equalsIgnoreCase(s)) return null;
  try {
    return UUID.fromString(s.trim());
  } catch (IllegalArgumentException ex) {
    // Si no tienes una BadRequestException propia, usa ResponseStatusException:
    throw new org.springframework.web.server.ResponseStatusException(
        org.springframework.http.HttpStatus.BAD_REQUEST,
        "hotelId inválido: " + s
    );
  }
}



  @PatchMapping("/{id}/hotel")
  public RestauranteResponse vincularHotel(@PathVariable UUID id, @Valid @RequestBody VincularHotelRequest req) {
    return toResp(vincular.vincular(id, req.getHotelId()));
  }

  @DeleteMapping("/{id}/hotel")
  public RestauranteResponse desvincularHotel(@PathVariable UUID id) {
    return toResp(desvincular.desvincular(id));
  }

  private static RestauranteResponse toResp(Restaurante r){
    return new RestauranteResponse(r.getId(), r.getHotelId(), r.getNombre(), r.getDireccion(),
        r.getImpuestoPorc(), r.getPropinaPorcDefault(), r.isEnabled());
  }
}