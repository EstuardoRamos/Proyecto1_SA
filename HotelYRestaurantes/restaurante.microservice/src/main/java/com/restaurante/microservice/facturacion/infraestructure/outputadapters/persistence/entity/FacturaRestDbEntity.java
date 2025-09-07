// com/restaurante/microservice/facturacion/infrastructure/outputadapters/persistence/entity/FacturaRestDbEntity.java
package com.restaurante.microservice.facturacion.infraestructure.outputadapters.persistence.entity;

import com.restaurante.microservice.facturacion.domain.EstadoFactura;
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
@Table(name = "restaurant_invoices",
        uniqueConstraints = @UniqueConstraint(name = "uk_rest_invoice_num", columnNames = {"restaurante_id", "serie", "numero"}))
public class FacturaRestDbEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36)
    private UUID id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "restaurante_id", length = 36, nullable = false)
    private UUID restauranteId;
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "cuenta_id", length = 36, nullable = false)
    private UUID cuentaId;

    @Column(length = 3, nullable = false)
    private String moneda;
    @Column(length = 10, nullable = false)
    private String serie;
    @Column(nullable = false)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private EstadoFactura estado;

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
    private List<FacturaItemRestDbEntity> items = new ArrayList<>();
}
