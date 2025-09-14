package com.user.microservice.user.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import lombok.Getter;

@Getter
public class Usuario {

    private final UUID id;
    private String nombre;
    private String email;     // único
    private String password;  // hash
    private String dpi;       // único
    private Rol rol;
    private boolean enabled;
    private final Instant createdAt;
    private Instant updatedAt;

    public Usuario(UUID id, String nombre, String email, String passwordHash,
            String dpi, Rol rol, boolean enabled, Instant createdAt, Instant updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.nombre = Objects.requireNonNull(nombre);
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(passwordHash);
        this.dpi = Objects.requireNonNull(dpi);
        this.rol = Objects.requireNonNull(rol);
        this.enabled = enabled;
        this.createdAt = createdAt == null ? Instant.now() : createdAt;
        this.updatedAt = updatedAt == null ? this.createdAt : updatedAt;
    }

    public static Usuario nuevo(String nombre, String email, String passwordHash, String dpi, Rol rol) {
        return new Usuario(UUID.randomUUID(), nombre, email, passwordHash, dpi, rol, true, Instant.now(), Instant.now());
    }

    public void actualizar(String nombre, Rol rol, Boolean enabled) {
        if (nombre != null && !nombre.isBlank()) {
            this.nombre = nombre.trim();
        }
        if (rol != null) {
            this.rol = rol;
        }
        if (enabled != null) {
            this.enabled = enabled;
        }
        this.updatedAt = Instant.now();
    }

    public void cambiarPasswordHash(String newHash) {
        this.password = Objects.requireNonNull(newHash);
        this.updatedAt = Instant.now();
    }
}
