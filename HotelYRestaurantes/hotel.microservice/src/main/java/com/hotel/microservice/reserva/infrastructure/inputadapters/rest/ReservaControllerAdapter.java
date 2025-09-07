package com.hotel.microservice.reserva.infrastructure.inputadapters.rest;

import com.hotel.microservice.common.errors.NotFoundException;
import com.hotel.microservice.reserva.application.inputports.*;
import com.hotel.microservice.reserva.domain.Reserva;
import com.hotel.microservice.reserva.infrastructure.inputadapters.rest.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/reservas")
public class ReservaControllerAdapter {

  private final CrearReservaInputPort crear;
  private final ListarReservasInputPort listar;
  private final ObtenerReservaInputPort obtener;
  private final CancelarReservaInputPort cancelar;
  private final CheckInInputPort checkIn;
  private final CheckOutInputPort checkOut;

  public ReservaControllerAdapter(CrearReservaInputPort crear, ListarReservasInputPort listar,
                                  ObtenerReservaInputPort obtener, CancelarReservaInputPort cancelar,
                                  CheckInInputPort checkIn, CheckOutInputPort checkOut) {
    this.crear = crear; this.listar = listar; this.obtener = obtener;
    this.cancelar = cancelar; this.checkIn = checkIn; this.checkOut = checkOut;
  }

  @PostMapping
  public ReservaResponse crear(@Valid @RequestBody CrearReservaRequest req) {
    var r = Reserva.nueva(req.hotelId(), req.habitacionId(), req.clienteId(),
        req.entrada(), req.salida(), req.huespedes());
    return toResp(crear.crear(r));
  }

  @GetMapping
  public Page<ReservaResponse> listar(@RequestParam UUID hotelId,
                                      @RequestParam(defaultValue="0") int page,
                                      @RequestParam(defaultValue="20") int size) {
    return listar.listar(hotelId, PageRequest.of(page, size)).map(ReservaControllerAdapter::toResp);
  }

  @GetMapping("/{id}")
  public ReservaResponse obtener(@PathVariable UUID id) {
    var r = obtener.obtener(id).orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
    return toResp(r);
  }

  @PatchMapping("/{id}/cancelar")
  public void cancelar(@PathVariable UUID id) { cancelar.cancelar(id); }

  @PostMapping("/{id}/checkin")
  public void checkIn(@PathVariable UUID id) { checkIn.checkIn(id); }

  @PostMapping("/{id}/checkout")
  public void checkOut(@PathVariable UUID id) { checkOut.checkOut(id); }

  private static ReservaResponse toResp(Reserva r){
    return new ReservaResponse(r.getId(), r.getHotelId(), r.getHabitacionId(), r.getClienteId(),
        r.getEntrada(), r.getSalida(), r.getHuespedes(), r.getEstado().name(), r.getTotal());
  }
}