// infrastrucuture/outputadapters/persistence/ConsumoRepositorioOutputAdapter.java
package com.restaurante.microservice.consumo.infrastructure.outputadapters.persistence;

import com.restaurante.microservice.consumo.application.outputports.persistence.ConsumoRepositorioPort;
import com.restaurante.microservice.consumo.domain.ConsumoItem;
import com.restaurante.microservice.consumo.infrastructure.outputadapters.persistence.entity.ConsumoDbEntity;
import com.restaurante.microservice.consumo.infrastructure.outputadapters.persistence.repository.ConsumoJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class ConsumoRepositorioOutputAdapter implements ConsumoRepositorioPort {

    private final ConsumoJpaRepository jpa;

    public ConsumoRepositorioOutputAdapter(ConsumoJpaRepository jpa) {
        this.jpa = jpa;
    }

    private static ConsumoItem toDomain(ConsumoDbEntity e) {
        return ConsumoItem.rehidratar(e.getId(), e.getCuentaId(), e.getPlatilloId(), e.getNombre(),
                e.getPrecioUnitario(), e.getCantidad(), e.getSubtotal(), e.getNota(), e.getCreatedAt(), e.getUpdatedAt());
    }

    private static ConsumoDbEntity toEntity(ConsumoItem i) {
        var e = new ConsumoDbEntity();
        e.setId(i.getId());
        e.setCuentaId(i.getCuentaId());
        e.setPlatilloId(i.getPlatilloId());
        e.setNombre(i.getNombre());
        e.setPrecioUnitario(i.getPrecioUnitario());
        e.setCantidad(i.getCantidad());
        e.setSubtotal(i.getSubtotal());
        e.setNota(i.getNota());
        e.setCreatedAt(i.getCreatedAt());
        e.setUpdatedAt(i.getUpdatedAt());
        return e;
    }

    @Override
    public ConsumoItem guardar(ConsumoItem i) {
        return toDomain(jpa.save(toEntity(i)));
    }

    @Override
    public Optional<ConsumoItem> porId(UUID id) {
        return jpa.findById(id).map(ConsumoRepositorioOutputAdapter::toDomain);
    }

    @Override
    public void eliminar(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public Page<ConsumoItem> listarPorCuenta(UUID cuentaId, Pageable p) {
        return jpa.findByCuentaId(cuentaId, p).map(ConsumoRepositorioOutputAdapter::toDomain);
    }

    @Override
    public BigDecimal sumarSubtotalPorCuenta(UUID cuentaId) {
        return jpa.sumSubtotal(cuentaId);
    }
}
