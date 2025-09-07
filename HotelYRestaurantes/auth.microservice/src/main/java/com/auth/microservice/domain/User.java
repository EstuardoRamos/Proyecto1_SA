package com.auth.microservice.domain;

import java.util.UUID;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;

@Getter
public class User {
    private UUID id;
    private String email;
    private String username;
    private String passwordHash;
    private boolean enabled;
    private Instant createdAt;
    private Set<String> roles;
    private int failedLoginAttempts;

    /** Constructor “completo” (sin cosas inexistentes). */
    public User(UUID id, String email, String username, String passwordHash,
                boolean enabled, Instant createdAt, Set<String> roles, int failedLoginAttempts) {
        this.id = id != null ? id : UUID.randomUUID();
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.enabled = enabled;
        this.createdAt = (createdAt != null) ? createdAt : Instant.now();
        this.roles = (roles != null) ? new HashSet<>(roles) : new HashSet<>();
        this.failedLoginAttempts = failedLoginAttempts;
    }

    /** Constructor para cargas/pruebas con roles vacíos y habilitado. */
    public User(UUID id, String email, String username, String passwordHash) {
        this(id, email, username, passwordHash, true, Instant.now(), new HashSet<>(), 0);
    }

    /** Constructor de registro: genera UUID y fecha automáticamente. */
    public User(String email, String username, String passwordHash) {
        this(UUID.randomUUID(), email, username, passwordHash, true, Instant.now(), new HashSet<>(), 0);
    }

    // --------- Comportamiento de dominio ----------
    public void enable() { this.enabled = true; }
    public void disable() { this.enabled = false; }

    public void changePassword(String newPasswordHash) {
        this.passwordHash = Objects.requireNonNull(newPasswordHash, "newPasswordHash no puede ser null");
    }

    public boolean assignRole(String roleName) {
        if (roleName == null || roleName.isBlank()) return false;
        if (this.roles == null) this.roles = new HashSet<>();
        return this.roles.add(roleName);
    }

    public boolean removeRole(String roleName) {
        return this.roles != null && this.roles.remove(roleName);
    }

    public boolean hasRole(String roleName) {
        return this.roles != null && this.roles.contains(roleName);
    }

    public void incrementFailedLoginAttempts() { this.failedLoginAttempts++; }
    public void resetFailedLoginAttempts() { this.failedLoginAttempts = 0; }

    public void updateProfile(String newUsername, String newEmail) {
        if (newUsername != null && !newUsername.isBlank()) this.username = newUsername;
        if (newEmail != null && !newEmail.isBlank()) this.email = newEmail;
    }
}
