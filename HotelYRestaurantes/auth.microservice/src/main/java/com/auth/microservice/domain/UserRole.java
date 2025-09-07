/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author estuardoramos
 */
public class UserRole {
    private UUID id;
    private UUID userId;
    private UUID roleId;
    private Instant assignedAt;
    private boolean revoked; // opcional para soportar soft-delete

    // ---- Constructores ----
    public UserRole(UUID id, UUID userId, UUID roleId, Instant assignedAt, boolean revoked) {
        this.id = Objects.requireNonNull(id, "id no puede ser null");
        this.userId = Objects.requireNonNull(userId, "userId no puede ser null");
        this.roleId = Objects.requireNonNull(roleId, "roleId no puede ser null");
        this.assignedAt = assignedAt != null ? assignedAt : Instant.now();
        this.revoked = revoked;
    }

    /** Crea un vínculo activo con id y fecha generados. */
    public UserRole(UUID userId, UUID roleId) {
        this(UUID.randomUUID(), userId, roleId, Instant.now(), false);
    }

    // ---- Comportamiento de dominio ----
    public void revoke() { this.revoked = true; }

    public boolean isActive() { return !this.revoked; }

    /** Útil para evitar duplicados (misma pareja user-role). */
    public boolean samePairAs(UUID uId, UUID rId) {
        return this.userId.equals(uId) && this.roleId.equals(rId);
    }
    
}
