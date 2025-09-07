// com/restaurante/microservice/facturacion/infrastructure/outputadapters/persistence/FacturaRepositorioOutputAdapter.java
package com.restaurante.microservice.facturacion.infraestructure.outputadapters.persistence;

import com.restaurante.microservice.facturacion.application.outputports.FacturaRepositorioPort;
import com.restaurante.microservice.facturacion.domain.*;
import com.restaurante.microservice.facturacion.infraestructure.outputadapters.persistence.entity.FacturaItemRestDbEntity;
import com.restaurante.microservice.facturacion.infraestructure.outputadapters.persistence.entity.FacturaRestDbEntity;
import com.restaurante.microservice.facturacion.infraestructure.outputadapters.persistence.repository.FacturaRestJpaRepository;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FacturaRepositorioOutputAdapter implements FacturaRepositorioPort {

    private final FacturaRestJpaRepository jpa;

    @Override
    public Factura guardar(Factura f) {
        var e = toEntity(f);
        var saved = jpa.save(e);
        return toDomain(saved);
    }

    @Override
    public Optional<Factura> porId(UUID id) {
        return jpa.findById(id).map(FacturaRepositorioOutputAdapter::toDomain);
    }

    @Override
    public Optional<Factura> porCuentaId(UUID cuentaId) {
        return jpa.findByCuentaId(cuentaId).map(FacturaRepositorioOutputAdapter::toDomain);
    }

    @Override
    public List<Factura> listar(UUID restauranteId, java.time.Instant desde, java.time.Instant hasta) {
        return jpa.listar(restauranteId, desde, hasta).stream().map(FacturaRepositorioOutputAdapter::toDomain).toList();
    }

    @Override
    public int maxNumeroEnSerie(UUID restauranteId, String serie) {
        return jpa.maxNumero(restauranteId, serie);
    }

    // mappers
    private static Factura toDomain(FacturaRestDbEntity e) {
        var items = e.getItems().stream()
                .map(i -> FacturaItem.rehidratar(i.getId(), i.getNombre(), i.getPrecioUnitario(), i.getCantidad(), i.getSubtotal()))
                .collect(Collectors.toList());
        return Factura.rehidratar(e.getId(), e.getRestauranteId(), e.getCuentaId(), e.getMoneda(), e.getSerie(), e.getNumero(),
                e.getClienteNit(), e.getClienteNombre(), e.getSubtotal(), e.getImpuesto(), e.getPropina(), e.getTotal(),
                e.getCreatedAt(), e.getEstado(), items);
    }

    private static FacturaRestDbEntity toEntity(Factura f) {
        var e = new FacturaRestDbEntity();
        e.setId(f.getId());
        e.setRestauranteId(f.getRestauranteId());
        e.setCuentaId(f.getCuentaId());
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
            var ie = new FacturaItemRestDbEntity();
            ie.setId(it.getId());
            ie.setNombre(it.getNombre());
            ie.setPrecioUnitario(it.getPrecioUnitario());
            ie.setCantidad(it.getCantidad());
            ie.setSubtotal(it.getSubtotal());
            return ie;
        }).toList();
        e.setItems(new ArrayList<>(items));
        return e;
    }
}
