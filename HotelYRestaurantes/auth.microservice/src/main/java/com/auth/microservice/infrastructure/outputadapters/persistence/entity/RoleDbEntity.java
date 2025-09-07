/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.infrastructure.outputadapters.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 *
 * @author estuardoramos
 */
@Entity
@Table(name = "roles")
@Data
public class RoleDbEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)          // <- Hibernate tratará el UUID como texto
    @Column(name = "id", length = 36, nullable = false)
    private UUID id;
    @Column(unique = true)
    private String name;
}
