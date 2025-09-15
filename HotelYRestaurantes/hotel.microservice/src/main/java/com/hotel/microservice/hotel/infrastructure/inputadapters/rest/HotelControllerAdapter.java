package com.hotel.microservice.hotel.infrastructure.inputadapters.rest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.microservice.hotel.application.inputports.ActualizarHotelInputPort;
import com.hotel.microservice.hotel.application.inputports.CrearHotelInputPort;
import com.hotel.microservice.hotel.application.inputports.EliminarHotelInputPort;
import com.hotel.microservice.hotel.application.inputports.ListarHotelesInputPort;
import com.hotel.microservice.hotel.application.inputports.ObtenerHotelInputPort;
import com.hotel.microservice.hotel.domain.Hotel;
import com.hotel.microservice.hotel.infrastructure.inputadapters.rest.dto.ActualizarHotelRequest;
import com.hotel.microservice.hotel.infrastructure.inputadapters.rest.dto.CrearHotelRequest;
import com.hotel.microservice.hotel.infrastructure.inputadapters.rest.dto.HotelResponse;

@CrossOrigin(origins = "http://frontend-comerdormir.s3-website.us-east-2.amazonaws.com", allowCredentials = "true")
@RestController
@RequestMapping("/v1/hoteles")
public class HotelControllerAdapter {

  private final CrearHotelInputPort crear;
  private final ListarHotelesInputPort listar;
  private final ObtenerHotelInputPort obtener;
  private final ActualizarHotelInputPort actualizar;
  private final EliminarHotelInputPort eliminar;

  public HotelControllerAdapter(
      CrearHotelInputPort crear,
      ListarHotelesInputPort listar,
      ObtenerHotelInputPort obtener,
      ActualizarHotelInputPort actualizar,
      EliminarHotelInputPort eliminar
  ) {
    this.crear = crear; this.listar = listar; this.obtener = obtener;
    this.actualizar = actualizar; this.eliminar = eliminar;
  }

  @PostMapping
  public ResponseEntity<HotelResponse> crear(@RequestBody CrearHotelRequest req) {
    var dir = new Hotel.Direccion(req.pais(), req.ciudad(), req.linea1(), req.linea2(), req.codigoPostal());
    var pol = new Hotel.Politicas(req.checkInDesde(), req.checkOutHasta());
    var h = crear.crear(req.nombre(), req.estrellas() == null ? 3 : req.estrellas(), dir, pol);
    return ResponseEntity.ok(toResponse(h));
  }

  @GetMapping
  public List<HotelResponse> listar() {
    return listar.listar().stream().map(HotelControllerAdapter::toResponse).collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public HotelResponse obtener(@PathVariable UUID id) {
    return toResponse(obtener.obtener(id));
  }

  @PutMapping("/{id}")
  public HotelResponse actualizar(@PathVariable UUID id, @RequestBody ActualizarHotelRequest req) {
    var dir = (req.pais()!=null || req.ciudad()!=null || req.linea1()!=null || req.linea2()!=null || req.codigoPostal()!=null)
        ? new Hotel.Direccion(req.pais(), req.ciudad(), req.linea1(), req.linea2(), req.codigoPostal())
        : null;
    var pol = (req.checkInDesde()!=null || req.checkOutHasta()!=null)
        ? new Hotel.Politicas(req.checkInDesde(), req.checkOutHasta())
        : null;
    var h = actualizar.actualizar(id, req.nombre(), req.estrellas(), dir, pol);
    return toResponse(h);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
    eliminar.eliminar(id);
    return ResponseEntity.noContent().build();
  }

  private static HotelResponse toResponse(Hotel h) {
    var d = h.getDireccion();
    var p = h.getPoliticas();
    return new HotelResponse(
        h.getId(), h.getNombre(), h.getEstrellas(), h.isActivo(),
        d==null?null:d.pais(), d==null?null:d.ciudad(), d==null?null:d.linea1(), d==null?null:d.linea2(), d==null?null:d.codigoPostal(),
        p==null?null:p.checkInDesde(), p==null?null:p.checkOutHasta()
    );
  }
}