// infrastructure/outputadapters/persistence/FacturaHotelRepositorioOutputAdapter.java
package com.hotel.microservice.facturacion.infrastructure.outputadapters.persistence;

import com.hotel.microservice.facturacion.application.outputports.persistence.FacturaHotelRepositorioPort;
import com.hotel.microservice.facturacion.domain.*;
import com.hotel.microservice.facturacion.infrastructure.outputadapters.persistence.entity.*;
import com.hotel.microservice.facturacion.infrastructure.outputadapters.persistence.repository.FacturaHotelJpaRepository;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FacturaHotelRepositorioOutputAdapter implements FacturaHotelRepositorioPort {

    private final FacturaHotelJpaRepository jpa;

    @Override
    public FacturaHotel guardar(FacturaHotel f) {
        var e = toEntity(f);
        var saved = jpa.save(e);
        return toDomain(saved);
    }

    @Override
    public Optional<FacturaHotel> porId(UUID id) {
        return jpa.findById(id).map(FacturaHotelRepositorioOutputAdapter::toDomain);
    }

    @Override
    public Optional<FacturaHotel> porReservaId(UUID reservaId) {
        return jpa.findByReservaId(reservaId).map(FacturaHotelRepositorioOutputAdapter::toDomain);
    }

    @Override
    public java.util.List<FacturaHotel> listar(UUID hotelId, java.time.Instant desde, java.time.Instant hasta) {
        return jpa.listar(hotelId, desde, hasta).stream().map(FacturaHotelRepositorioOutputAdapter::toDomain).toList();
    }

    @Override
    public int maxNumeroEnSerie(UUID hotelId, String serie) {
        return jpa.maxNumero(hotelId, serie);
    }

    private static FacturaHotel toDomain(FacturaHotelDbEntity e) {
        var items = e.getItems().stream()
                .map(i -> FacturaHotelItem.rehidratar(i.getId(), i.getDescripcion(), i.getPrecioUnitario(), i.getCantidad(), i.getSubtotal()))
                .collect(Collectors.toList());
        return FacturaHotel.rehidratar(e.getId(), e.getHotelId(), e.getReservaId(), e.getMoneda(), e.getSerie(), e.getNumero(),
                e.getClienteNit(), e.getClienteNombre(), e.getSubtotal(), e.getImpuesto(), e.getPropina(), e.getTotal(),
                e.getCreatedAt(), e.getEstado(), items);
    }

    private static FacturaHotelDbEntity toEntity(FacturaHotel f) {
        var e = new FacturaHotelDbEntity();
        e.setId(f.getId());
        e.setHotelId(f.getHotelId());
        e.setReservaId(f.getReservaId());
        e.setMoneda(f.getMoneda());
        e.setSerie(f.getSerie());
        e.setNumero(f.getNumero());
        e.setEstado(f.getEstado());
        e.setClienteNit(f.getClienteNit());
        e.setClienteNombre(f.getClienteNombre());
        e.setSubtotal(f.getSubtotal());
        e.setImpuesto(f.getImpuesto());
        e.setPropina(f.getPropina());
        e.setTotal(f.getTotal());
        e.setCreatedAt(f.getCreatedAt());
        var items = f.getItems().stream().map(it -> {
            var ie = new FacturaHotelItemDbEntity();
            ie.setId(it.getId());
            ie.setDescripcion(it.getDescripcion());
            ie.setPrecioUnitario(it.getPrecioUnitario());
            ie.setCantidad(it.getCantidad());
            ie.setSubtotal(it.getSubtotal());
            return ie;
        }).toList();
        e.setItems(new java.util.ArrayList<>(items));
        return e;
    }
}
