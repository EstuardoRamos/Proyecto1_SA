package com.hotel.microservice.hotel.infrastructure.outputadapters.persistence;

import com.hotel.microservice.hotel.application.outputports.HotelRepositorioPort;
import com.hotel.microservice.hotel.domain.Hotel;
import com.hotel.microservice.hotel.infrastructure.outputadapters.persistence.entity.HotelDbEntity;
import com.hotel.microservice.hotel.infrastructure.outputadapters.persistence.repository.HotelJpaRepository;
import java.util.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class HotelRepositorioOutputAdapter implements HotelRepositorioPort {

    private final HotelJpaRepository jpa;

    public HotelRepositorioOutputAdapter(HotelJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return jpa.existsByNombreIgnoreCase(nombre);
    }

    @Override
    public Hotel guardar(Hotel h) {
        var e = toEntity(h);
        var saved = jpa.save(e);
        return toDomain(saved);
    }

    @Override
    public Optional<Hotel> buscarPorId(UUID id) {
        return jpa.findById(id).map(HotelRepositorioOutputAdapter::toDomain);
    }

    @Override
    public List<Hotel> listarActivos() {
        return jpa.findAllByActivoTrueOrderByNombreAsc().stream()
                .map(HotelRepositorioOutputAdapter::toDomain)
                .toList();
    }

    @Override
    public void desactivar(UUID id) {
        var e = jpa.findById(id).orElseThrow();
        e.setActivo(false);
        jpa.save(e);
    }

    // ---- mappers ----
    private static Hotel toDomain(HotelDbEntity e) {
        var dir = new Hotel.Direccion(e.getPais(), e.getCiudad(), e.getLinea1(), e.getLinea2(), e.getCodigoPostal());
        var pol = new Hotel.Politicas(e.getCheckInDesde(), e.getCheckOutHasta());
        return new Hotel(e.getId(), e.getNombre(), e.getEstrellas(), dir, pol, null, null, e.isActivo());
    }

    private static HotelDbEntity toEntity(Hotel h) {
        var e = new HotelDbEntity();
        e.setId(h.getId());
        e.setNombre(h.getNombre());
        e.setEstrellas(h.getEstrellas());
        e.setActivo(h.isActivo());
        if (h.getDireccion() != null) {
            e.setPais(h.getDireccion().pais());
            e.setCiudad(h.getDireccion().ciudad());
            e.setLinea1(h.getDireccion().linea1());
            e.setLinea2(h.getDireccion().linea2());
            e.setCodigoPostal(h.getDireccion().codigoPostal());
        }
        if (h.getPoliticas() != null) {
            e.setCheckInDesde(h.getPoliticas().checkInDesde());
            e.setCheckOutHasta(h.getPoliticas().checkOutHasta());
        }
        return e;
    }
}
