package com.hotel.microservice.facturacion.infrastructure.outputadapters.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "hotel_invoice_items")
public class FacturaHotelItemDbEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36)
    private UUID id;

    @Column(nullable = false, length = 220)
    private String descripcion;
    @Column(name = "precio_unitario", precision = 12, scale = 2, nullable = false)
    private BigDecimal precioUnitario;
    @Column(nullable = false)
    private int cantidad;
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;
}
