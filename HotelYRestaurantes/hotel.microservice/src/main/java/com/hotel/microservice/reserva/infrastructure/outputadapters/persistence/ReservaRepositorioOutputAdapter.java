package com.hotel.microservice.reserva.infrastructure.outputadapters.persistence;

import com.hotel.microservice.reserva.application.outputports.ReservaRepositorioPort;
import com.hotel.microservice.reserva.domain.Reserva;
import com.hotel.microservice.reserva.infrastructure.outputadapters.persistence.entity.ReservaDbEntity;
import com.hotel.microservice.reserva.infrastructure.outputadapters.persistence.repository.ReservaJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class ReservaRepositorioOutputAdapter implements ReservaRepositorioPort {
  private final ReservaJpaRepository jpa;
  public ReservaRepositorioOutputAdapter(ReservaJpaRepository jpa){ this.jpa=jpa; }

  private static Reserva toDomain(ReservaDbEntity e){
    return new Reserva(e.getId(), e.getHotelId(), e.getHabitacionId(), e.getClienteId(),
        e.getEntrada(), e.getSalida(), e.getHuespedes(),
        Reserva.Estado.valueOf(e.getEstado()), e.getTotal());
  }
  private static ReservaDbEntity toEntity(Reserva r){
    var e=new ReservaDbEntity();
    e.setId(r.getId()); e.setHotelId(r.getHotelId()); e.setHabitacionId(r.getHabitacionId());
    e.setClienteId(r.getClienteId()); e.setEntrada(r.getEntrada()); e.setSalida(r.getSalida());
    e.setHuespedes(r.getHuespedes()); e.setEstado(r.getEstado().name()); e.setTotal(r.getTotal());
    return e;
  }

  @Override public Reserva guardar(Reserva r){ return toDomain(jpa.save(toEntity(r))); }
  @Override public Optional<Reserva> porId(UUID id){ return jpa.findById(id).map(ReservaRepositorioOutputAdapter::toDomain); }
  @Override public Page<Reserva> porHotel(UUID hotelId, Pageable pageable){ return jpa.findByHotelId(hotelId, pageable).map(ReservaRepositorioOutputAdapter::toDomain); }
  @Override public boolean existeTraslape(UUID roomId, LocalDate in, LocalDate out, Set<Reserva.Estado> estados){
    var ls = estados.stream().map(Enum::name).toList();
    return jpa.existsOverlap(roomId, in, out, ls);
  }
  @Override public void actualizarEstado(UUID id, Reserva.Estado estado){
    jpa.findById(id).ifPresent(e->{ e.setEstado(estado.name()); jpa.save(e); });
  }
}