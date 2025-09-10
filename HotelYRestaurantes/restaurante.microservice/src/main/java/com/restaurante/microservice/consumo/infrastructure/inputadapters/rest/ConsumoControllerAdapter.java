package com.restaurante.microservice.consumo.infrastructure.inputadapters.rest;

// infrastrucuture/inputadapters/rest/ConsumoControllerAdapter.java

import com.restaurante.microservice.consumo.application.inputports.*;
import com.restaurante.microservice.consumo.infrastructure.inputadapters.rest.dto.ActualizarConsumoRequest;
import com.restaurante.microservice.consumo.infrastructure.inputadapters.rest.dto.ConsumoResponse;
import com.restaurante.microservice.consumo.infrastructure.inputadapters.rest.dto.CrearConsumoRequest;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ConsumoControllerAdapter {

  private final CrearConsumoInputPort crear;
  private final ActualizarConsumoInputPort actualizar;
  private final EliminarConsumoInputPort eliminar;
  private final ObtenerConsumoInputPort obtener;
  private final ListarConsumosInputPort listar;

  
  @Operation(summary = "Crear consumo")        
  @PostMapping("/cuentas/{cuentaId}/consumos")
  public ConsumoResponse crear(@PathVariable UUID cuentaId, @RequestBody CrearConsumoRequest req) {
    var it = crear.crear(cuentaId, req.platilloId(), req.nombre(), req.precioUnitario(), req.cantidad(), req.nota());
    return ConsumoResponse.from(it);
  }
  
  
  @Operation(summary = "Obtener consumo por ID")
  @GetMapping("/consumos/{id}")
  public ConsumoResponse porId(@PathVariable UUID id) {
    var it = obtener.porId(id).orElseThrow(() -> new com.restaurante.microservice.common.errors.NotFoundException("Ítem no encontrado"));
    return ConsumoResponse.from(it);
  }

  // Solo arreglo (como en Restaurantes/Cuentas)
  @Operation(summary = "Listar consumos de una cuenta")
  @GetMapping("/cuentas/{cuentaId}/consumos")
  public java.util.List<ConsumoResponse> listar(@PathVariable UUID cuentaId,
                                                @ParameterObject org.springframework.data.domain.Pageable pageable) {
    return listar.porCuenta(cuentaId, pageable).map(ConsumoResponse::from).getContent();
  }
  
  @Operation(summary = "Actualizar un consumo")
  @PutMapping("/consumos/{id}")
  public ConsumoResponse actualizar(@PathVariable UUID id, @RequestBody ActualizarConsumoRequest req) {
    var it = actualizar.actualizar(id, req.precioUnitario(), req.cantidad(), req.nota());
    return ConsumoResponse.from(it);
  }
  
  @Operation(summary = "Eliminar un consumo")
  @DeleteMapping("/consumos/{id}")
  public void eliminar(@PathVariable UUID id) { eliminar.eliminar(id); }
}