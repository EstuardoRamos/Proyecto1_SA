/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.infrastructure.outputadapters.persistence.entity;

/**
 *
 * @author estuardoramos
 */
import com.auth.microservice.application.outputports.persistance.RoleRepositoryOutputPort;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.stereotype.Component;

import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleRepositoryOutputAdapter implements RoleRepositoryOutputPort {

    private final RoleJpaRepository roles;

    @Override
    public Optional<String> findIdByName(String name) {
        return roles.findByName(name).map(r -> r.getId().toString());
    }

    @Override
    @Transactional
    public void ensureRoleExists(String name) {
        roles.findByName(name).orElseGet(() -> {
            var nr = new RoleDbEntity();      // ctor sin argumentos
            nr.setId(UUID.randomUUID());      // si NO usas @GeneratedValue en id
            nr.setName(name);
            try {
                return roles.save(nr);
            } catch (org.springframework.dao.DataIntegrityViolationException ignore) {
                // otro hilo lo insertó: idempotente
                return roles.findByName(name).orElseThrow();
            }
        });
    }

}
