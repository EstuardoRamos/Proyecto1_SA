// user/infrastructure/inputadapters/rest/UsuarioControllerAdapter.java
package com.user.microservice.user.infrastructure.inputadapters.rest;

import com.user.microservice.common.errors.NotFoundException;
import com.user.microservice.user.application.inputports.*;
import com.user.microservice.user.domain.Rol;
import com.user.microservice.user.infrastructure.inputadapters.rest.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import java.util.*;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/v1/users") @RequiredArgsConstructor
public class UsuarioControllerAdapter {
  private final ListarUsuariosInputPort listar;
  private final ObtenerUsuarioInputPort obtener;
  private final ActualizarUsuarioInputPort actualizar;
  private final CambiarEstadoUsuarioInputPort cambiarEstado;

  @Operation(summary="Obtener usuario por id")
  @GetMapping("/{id}")
  public UsuarioResponse porId(@PathVariable UUID id){
    var u = obtener.porId(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    return UsuarioResponse.from(u);
  }

  @Operation(summary="Listar usuarios (solo arreglo)")
  @GetMapping
  public List<UsuarioResponse> list(@RequestParam(required=false) String q,
                                    @RequestParam(required=false) Rol rol,
                                    @RequestParam(required=false) Boolean enabled,
                                    @ParameterObject Pageable pageable){
    return listar.listar(q, rol, enabled, pageable).map(UsuarioResponse::from).getContent();
  }

  @Operation(summary="Actualizar nombre/rol/enabled")
  @PutMapping("/{id}")
  public UsuarioResponse update(@PathVariable UUID id, @RequestBody ActualizarRequest req){
    var u = actualizar.actualizar(id, req.nombre(), req.rol(), req.enabled());
    return UsuarioResponse.from(u);
  }

  @Operation(summary="Habilitar/Deshabilitar")
  @PostMapping("/{id}/enabled")
  public UsuarioResponse setEnabled(@PathVariable UUID id, @RequestParam boolean value){
    return UsuarioResponse.from(cambiarEstado.habilitar(id, value));
  }
}