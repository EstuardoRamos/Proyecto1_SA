package com.hotel.microservice.habitacion.infrastructure.outputadapters.persistence;

import com.hotel.microservice.habitacion.application.outputports.HabitacionRepositorioPort;
import com.hotel.microservice.habitacion.domain.Habitacion;
import com.hotel.microservice.habitacion.infrastructure.outputadapters.persistence.entity.HabitacionDbEntity;
import com.hotel.microservice.habitacion.infrastructure.outputadapters.persistence.repository.HabitacionJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class HabitacionRepositorioOutputAdapter implements HabitacionRepositorioPort {

    private final HabitacionJpaRepository jpa;

    public HabitacionRepositorioOutputAdapter(HabitacionJpaRepository jpa) {
        this.jpa = jpa;
    }

    private static Habitacion toDomain(HabitacionDbEntity e) {
        return new Habitacion(
                e.getId(), e.getHotelId(), e.getNumero(), e.getTipo(),
                e.getCapacidad(), e.getPrecioBase(),
                Habitacion.Estado.valueOf(e.getEstado()),
                e.getDescripcion()
        );
    }

    private static HabitacionDbEntity toEntity(Habitacion h) {
        var e = new HabitacionDbEntity();
        e.setId(h.getId());
        e.setHotelId(h.getHotelId());
        e.setNumero(h.getNumero());
        e.setTipo(h.getTipo());
        e.setCapacidad(h.getCapacidad());
        e.setPrecioBase(h.getPrecioBase());
        e.setEstado(h.getEstado().name());
        e.setDescripcion(h.getDescripcion());
        return e;
    }

    @Override
    public Habitacion guardar(Habitacion h) {
        var saved = jpa.save(toEntity(h));
        return toDomain(saved);
    }

    @Override
    public Optional<Habitacion> porId(UUID id) {
        return jpa.findById(id).map(HabitacionRepositorioOutputAdapter::toDomain);
    }

    @Override
    public Page<Habitacion> porHotel(UUID hotelId, Pageable pageable) {
        return jpa.findByHotelId(hotelId, pageable).map(HabitacionRepositorioOutputAdapter::toDomain);
    }

    @Override
    public boolean existeNumeroEnHotel(UUID hotelId, String numero) {
        return jpa.existsByHotelIdAndNumero(hotelId, numero);
    }

    @Override
    public void eliminar(UUID id) {
        jpa.deleteById(id);
    }
}
