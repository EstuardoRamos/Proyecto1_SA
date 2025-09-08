package com.hotel.microservice.facturacion.infrastructure.outputadapters.persistence.entity;

import com.hotel.microservice.facturacion.domain.EstadoFacturaHotel;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "hotel_invoices",
        uniqueConstraints = @UniqueConstraint(name = "uk_hotel_invoice_num", columnNames = {"hotel_id", "serie", "numero"}))
public class FacturaHotelDbEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36)
    private java.util.UUID id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "hotel_id", length = 36, nullable = false)
    private java.util.UUID hotelId;
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "reserva_id", length = 36, nullable = false)
    private java.util.UUID reservaId;

    @Column(length = 3, nullable = false)
    private String moneda;
    @Column(length = 10, nullable = false)
    private String serie;
    @Column(nullable = false)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private EstadoFacturaHotel estado;

    @Column(length = 20)
    private String clienteNit;
    @Column(length = 150)
    private String clienteNombre;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal impuesto;
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal propina;
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "factura_id", nullable = false)
    private java.util.List<FacturaHotelItemDbEntity> items = new ArrayList<>();
}
