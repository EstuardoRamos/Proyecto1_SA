package com.restaurante.microservice.cuenta.infrastructure.inputadapters.rest;

import com.restaurante.microservice.common.errors.NotFoundException;
import com.restaurante.microservice.cuenta.application.inputports.*;
import com.restaurante.microservice.cuenta.domain.EstadoCuenta;
import com.restaurante.microservice.cuenta.infrastructure.inputadapters.rest.dto.AbrirCuentaRequest;
import com.restaurante.microservice.cuenta.infrastructure.inputadapters.rest.dto.CuentaResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://frontend-comerdormir.s3-website.us-east-2.amazonaws.com", allowCredentials = "true")
@RestController
@RequestMapping("/v1/cuentas")
@RequiredArgsConstructor
public class CuentaControllerAdapter {
  private final AbrirCuentaInputPort abrir;
  private final ObtenerCuentaInputPort obtener;
  private final ListarCuentasInputPort listar;
  private final CerrarCuentaInputPort cerrar;
  private final CobrarCuentaInputPort cobrar;

  @Operation(summary = "Abrir cuenta")
  @PostMapping
  public CuentaResponse abrir(@Valid @RequestBody AbrirCuentaRequest req) {
    return CuentaResponse.from(abrir.abrir(req.restauranteId(), req.mesa()));
  }

  @Operation(summary = "Obtener cuenta por ID")
  @GetMapping("/{id}")
  public CuentaResponse porId(@PathVariable UUID id) {
    return CuentaResponse.from(
        obtener.porId(id).orElseThrow(() -> new NotFoundException("Cuenta no encontrada")));
  }

  @Operation(summary = "Listar cuentas (opcional: filtrar por restaurante y estado)")
  @GetMapping
  public java.util.List<CuentaResponse> listar(
      @RequestParam(required=false) UUID restauranteId,
      @RequestParam(required=false) EstadoCuenta estado,
      @ParameterObject org.springframework.data.domain.Pageable pageable
  ) {
    return listar.listar(restauranteId, estado, pageable).map(CuentaResponse::from).getContent();
  }

  @Operation(summary = "Cerrar cuenta (pasa de ABIERTA a CERRADA)")
  @PostMapping("/{id}/cerrar")
  public CuentaResponse cerrar(@PathVariable UUID id) {
    return CuentaResponse.from(cerrar.cerrar(id));
  }

  @Operation(summary = "Cobrar cuenta (pasa de CERRADA a COBRADA)")
  @PostMapping("/{id}/cobrar")
  public CuentaResponse cobrar(@PathVariable UUID id) {
    return CuentaResponse.from(cobrar.cobrar(id));
  }
}