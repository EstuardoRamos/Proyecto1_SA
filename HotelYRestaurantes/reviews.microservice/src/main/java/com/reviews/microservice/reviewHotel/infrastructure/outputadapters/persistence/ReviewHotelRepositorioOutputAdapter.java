// infrastructure/outputadapters/persistence/ReviewHotelRepositorioOutputAdapter.java
package com.reviews.microservice.reviewHotel.infrastructure.outputadapters.persistence;

import com.reviews.microservice.reviewHotel.application.outputports.persistence.ReviewHotelRepositorioPort;
import com.reviews.microservice.reviewHotel.domain.AggResumen;
import com.reviews.microservice.reviewHotel.domain.ReviewHotel;
import com.reviews.microservice.reviewHotel.infrastructure.outputadapters.persistence.entity.ReviewHotelDbEntity;
import com.reviews.microservice.reviewHotel.infrastructure.outputadapters.persistence.repository.ReviewHotelJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Repository
public class ReviewHotelRepositorioOutputAdapter implements ReviewHotelRepositorioPort {

    private final ReviewHotelJpaRepository jpa;

    public ReviewHotelRepositorioOutputAdapter(ReviewHotelJpaRepository jpa) {
        this.jpa = jpa;
    }

    private static ReviewHotel toDomain(ReviewHotelDbEntity e) {
        return new ReviewHotel(
                e.getId(), e.getFacturaHotelId(), e.getHotelId(), e.getHabitacionId(), e.getClienteId(),
                e.getEstrellas(), e.getComentario(),
                e.getTags() == null ? List.of() : e.getTags().stream().toList(),
                e.isEnabled(), e.getCreatedAt(), e.getUpdatedAt()
        );
    }

    private static ReviewHotelDbEntity toEntity(ReviewHotel r) {
        return ReviewHotelDbEntity.builder()
                .id(r.getId())
                .facturaHotelId(r.getFacturaHotelId())
                .hotelId(r.getHotelId())
                .habitacionId(r.getHabitacionId())
                .clienteId(r.getClienteId())
                .estrellas(r.getEstrellas())
                .comentario(r.getComentario())
                .tags(r.getTags() == null ? Set.of() : new HashSet<>(r.getTags()))
                .enabled(r.isEnabled())
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .build();
    }

    @Override
    public ReviewHotel guardar(ReviewHotel r) {
        return toDomain(jpa.save(toEntity(r)));
    }

    @Override
    public boolean existsByFacturaHotelId(UUID id) {
        return jpa.existsByFacturaHotelId(id);
    }

    @Override
    public List<ReviewHotel> listarPorHotel(UUID hotelId) {
        return jpa.findByHotelIdOrderByCreatedAtDesc(hotelId).stream().map(ReviewHotelRepositorioOutputAdapter::toDomain).toList();
    }

    @Override
    public List<ReviewHotel> listarPorFactura(UUID facturaHotelId) {
        return jpa.findByFacturaHotelId(facturaHotelId).stream().map(ReviewHotelRepositorioOutputAdapter::toDomain).toList();
    }

    @Override
    public double promedioPorHotel(UUID hotelId) {
        var xs = listarPorHotel(hotelId);
        if (xs.isEmpty()) {
            return 0d;
        }
        return xs.stream().mapToInt(ReviewHotel::getEstrellas).average().orElse(0d);
    }

    @Override
    public long totalPorHotel(UUID hotelId) {
        return jpa.findByHotelIdOrderByCreatedAtDesc(hotelId).size();
    }

    @Override
    public Optional<ReviewHotel> porId(UUID id) {
        return jpa.findById(id).map(ReviewHotelRepositorioOutputAdapter::toDomain);
    }

    /*@Override
    public Agg resumen(UUID hotelId) {
        Object[] row = jpa.resumen(hotelId); // [Long, Double]
        long total = (row[0] == null) ? 0L : ((Number) row[0]).longValue();
        double avg = (row[1] == null) ? 0.0 : ((Number) row[1]).doubleValue();
        return new Agg(total, avg);
    }*/

    @Override
    public List<ReviewHotel> top(UUID hotelId, int limit) {
        var p = PageRequest.of(0, Math.max(1, limit),
                Sort.by(Sort.Order.desc("estrellas"), Sort.Order.desc("createdAt")));
        return jpa.findByHotelIdAndEnabled(hotelId, true, p)
                .map(ReviewHotelRepositorioOutputAdapter::toDomain)
                .getContent();
    }

    @Override
    public void setEnabled(UUID id, boolean enabled) {
        jpa.findById(id).ifPresent(e -> {
            e.setEnabled(enabled);
            e.setUpdatedAt(java.time.Instant.now());
            jpa.save(e);
        });
    }

    public Agg resumen(UUID hotelId) {
        Object[] row = jpa.resumen(hotelId); // [avg, count]
        double promedio = ((Number) row[0]).doubleValue();
        long total = ((Number) row[1]).longValue();
        return new Agg((long) promedio, total);
    }
}
