// infrastructure/outputadapters/persistence/entity/ReviewHotelDbEntity.java
package com.reviews.microservice.reviewHotel.infrastructure.outputadapters.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "review_hotel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewHotelDbEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36)
    private UUID id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "factura_hotel_id", length = 36, nullable = false, unique = true)
    private UUID facturaHotelId;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "hotel_id", length = 36, nullable = false)
    private UUID hotelId;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "habitacion_id", length = 36)
    private UUID habitacionId;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "cliente_id", length = 36)
    private UUID clienteId;

    @Column(nullable = false)
    private int estrellas;

    @Column(columnDefinition = "text")
    private String comentario;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "review_hotel_tags", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "tag", length = 50)
    private Set<String> tags;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;
}
