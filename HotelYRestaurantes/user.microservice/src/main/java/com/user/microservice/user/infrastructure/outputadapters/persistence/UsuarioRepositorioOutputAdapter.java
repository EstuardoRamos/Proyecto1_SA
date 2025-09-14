// user/infrastructure/outputadapters/persistence/UsuarioRepositorioOutputAdapter.java
package com.user.microservice.user.infrastructure.outputadapters.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.user.microservice.user.application.outputports.UsuarioRepositorioPort;
import com.user.microservice.user.domain.Usuario;
import com.user.microservice.user.infrastructure.outputadapters.persistence.entity.UsuarioDbEntity;
import com.user.microservice.user.infrastructure.outputadapters.persistence.repository.UsuarioJpaRepository;

@Repository
public class UsuarioRepositorioOutputAdapter implements UsuarioRepositorioPort {

    private final UsuarioJpaRepository jpa;

    public UsuarioRepositorioOutputAdapter(UsuarioJpaRepository jpa) {
        this.jpa = jpa;
    }

    private static Usuario toDomain(UsuarioDbEntity e) {
        return new Usuario(e.getId(), e.getNombre(), e.getEmail(), e.getPasswordHash(),
                e.getDpi(), e.getRol(), e.isEnabled(), e.getCreatedAt(), e.getUpdatedAt());
    }

    private static UsuarioDbEntity toEntity(Usuario u) {
        var e = new UsuarioDbEntity();
        e.setId(u.getId());
        e.setNombre(u.getNombre());
        e.setEmail(u.getEmail());
        e.setPasswordHash(u.getPassword());
        e.setDpi(u.getDpi());
        e.setRol(u.getRol());
        e.setEnabled(u.isEnabled());
        e.setCreatedAt(u.getCreatedAt());
        e.setUpdatedAt(u.getUpdatedAt());
        return e;
    }

    @Override
    public Usuario guardar(Usuario u) {
        return toDomain(jpa.save(toEntity(u)));
    }

    @Override
    public Optional<Usuario> porId(UUID id) {
        return jpa.findById(id).map(UsuarioRepositorioOutputAdapter::toDomain);
    }

    @Override
    public Optional<Usuario> porEmail(String email) {
        return jpa.findByEmail(email).map(UsuarioRepositorioOutputAdapter::toDomain);
    }

    @Override
    public boolean emailOcupado(String email) {
        return jpa.existsByEmail(email);
    }

    @Override
    public boolean dpiOcupado(String dpi) {
        return jpa.existsByDpi(dpi);
    }

    @Override
    public Page<Usuario> buscar(String q, com.user.microservice.user.domain.Rol rol, Boolean enabled, Pageable p) {
        var qq = (q != null && !q.isBlank()) ? q.trim() : null;
        String qNorm = (q == null || q.isBlank()) ? null : q.toLowerCase(java.util.Locale.ROOT);
        //return jpa.search(qNorm, rol, enabled, pageable);
        return jpa.search(qq, String.valueOf(rol), enabled, p).map(UsuarioRepositorioOutputAdapter::toDomain);
    }
}
