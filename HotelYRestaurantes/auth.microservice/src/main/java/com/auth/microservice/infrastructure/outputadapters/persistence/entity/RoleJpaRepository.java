/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.infrastructure.outputadapters.persistence.entity;

/**
 *
 * @author estuardoramos
 */
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface RoleJpaRepository extends JpaRepository<RoleDbEntity, UUID> {
  Optional<RoleDbEntity> findByName(String name);
}