// infrastructure/outputadapters/persistence/ReviewPlatilloRepositorioOutputAdapter.java
package com.reviews.microservice.reviewPlatillo.infrastructure.outputadapters.persistence;

import com.reviews.microservice.common.AggResumen;
import com.reviews.microservice.reviewPlatillo.application.outputports.persistence.ReviewPlatilloRepositorioPort;
import com.reviews.microservice.reviewPlatillo.domain.ReviewPlatillo;
import com.reviews.microservice.reviewPlatillo.infrastructure.outputadapters.persistence.entity.ReviewPlatilloDbEntity;
import com.reviews.microservice.reviewPlatillo.infrastructure.outputadapters.persistence.repository.ReviewPlatilloJpaRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ReviewPlatilloRepositorioOutputAdapter implements ReviewPlatilloRepositorioPort {

    private final ReviewPlatilloJpaRepository jpa;

    public ReviewPlatilloRepositorioOutputAdapter(ReviewPlatilloJpaRepository jpa) {
        this.jpa = jpa;
    }

    private static ReviewPlatillo toDomain(ReviewPlatilloDbEntity e) {
        return new ReviewPlatillo(e.getId(), e.getCuentaId(), e.getRestauranteId(), e.getPlatilloId(),
                e.getClienteId(), e.getEstrellas(), e.getComentario(), e.isEnabled(), e.getCreatedAt(), e.getUpdatedAt());
    }

    private static ReviewPlatilloDbEntity toEntity(ReviewPlatillo r) {
        return ReviewPlatilloDbEntity.builder()
                .id(r.getId()).cuentaId(r.getCuentaId()).restauranteId(r.getRestauranteId())
                .platilloId(r.getPlatilloId()).clienteId(r.getClienteId())
                .estrellas(r.getEstrellas()).comentario(r.getComentario())
                .enabled(r.isEnabled()).createdAt(r.getCreatedAt()).updatedAt(r.getUpdatedAt())
                .build();
    }

    @Override
    public ReviewPlatillo guardar(ReviewPlatillo r) {
        return toDomain(jpa.save(toEntity(r)));
    }

    @Override
    public Optional<ReviewPlatillo> porId(UUID id) {
        return jpa.findById(id).map(ReviewPlatilloRepositorioOutputAdapter::toDomain);
    }

    @Override
    public Page<ReviewPlatillo> listarPorPlatillo(UUID pid, Pageable p) {
        return jpa.findByPlatilloIdAndEnabledTrue(pid, p).map(ReviewPlatilloRepositorioOutputAdapter::toDomain);
    }

    @Override
    public boolean existsByCuentaAndPlatillo(UUID cuentaId, UUID platilloId) {
        return jpa.existsByCuentaIdAndPlatilloId(cuentaId, platilloId);
    }

    @Override
    public AggResumen resumen(UUID platilloId) {
        var pr = jpa.resumen(platilloId);
        return new AggResumen(pr.promedio(), pr.total());
    }
}
