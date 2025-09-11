// infrastructure/outputadapters/persistence/ReviewHotelRepositorioOutputAdapter.java
package com.reviews.microservice.reviewHotel.infrastructure.outputadapters.persistence;

import com.reviews.microservice.reviewHotel.application.outputports.persistence.ReviewHotelRepositorioPort;
import com.reviews.microservice.reviewHotel.domain.ReviewHotel;
import com.reviews.microservice.reviewHotel.infrastructure.outputadapters.persistence.entity.ReviewHotelDbEntity;
import com.reviews.microservice.reviewHotel.infrastructure.outputadapters.persistence.repository.ReviewHotelJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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
}
