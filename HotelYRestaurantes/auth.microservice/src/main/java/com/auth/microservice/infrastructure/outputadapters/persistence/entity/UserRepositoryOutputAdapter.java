/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.infrastructure.outputadapters.persistence.entity;

/**
 *
 * @author estuardoramos
 */
import com.auth.microservice.infrastructure.outputadapters.persistence.repository.UserJpaRepository;
import com.auth.microservice.application.outputports.persistance.UserRepositoryOutputPort;
import com.auth.microservice.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Component
public class UserRepositoryOutputAdapter implements UserRepositoryOutputPort {

    private final UserJpaRepository users;
    private final RoleJpaRepository roles;

    public UserRepositoryOutputAdapter(UserJpaRepository users, RoleJpaRepository roles) {
        this.users = users;
        this.roles = roles;
    }

    private User toDomain(UserDbEntity e) {
        var roleNames = (e.getRoles() == null)
                ? java.util.Set.<String>of()
                : e.getRoles().stream()
                        .map(RoleDbEntity::getName)
                        .collect(java.util.stream.Collectors.toSet());

        return new User(
                e.getId(),
                e.getEmail(),
                e.getUsername(),
                e.getPasswordHash(),
                e.isEnabled(),
                e.getCreatedAt(),
                roleNames,
                0
        );
    }

    private static UserDbEntity toEntity(User u) {
        var e = new UserDbEntity();
        e.setId(u.getId());
        e.setEmail(u.getEmail());
        e.setUsername(u.getUsername());
        e.setPasswordHash(u.getPasswordHash());
        e.setEnabled(u.isEnabled());
        e.setCreatedAt(Objects.requireNonNullElseGet(u.getCreatedAt(), Instant::now));
        return e;
    }

    @Override
    public Optional<User> findByEmailOrUsername(String k) {
        return users.findByEmailOrUsername(k).map(this::toDomain);
    }

    @Override
    public Optional<User> findById(String id) {
        try {
            var uuid = UUID.fromString(id);                 // String -> UUID
            return users.findById(uuid).map(this::toDomain);
        } catch (IllegalArgumentException invalid) {
            return Optional.empty(); // id no es un UUID válido
        }
    }

    @Override
    public User save(User u) {
        var saved = users.save(toEntity(u));
        return toDomain(saved);
    }

    @Override
    @Transactional
    public void addRole(String userId, String roleName) {
        var u = users.findById(UUID.fromString(userId)).orElseThrow(); // String -> UUID

        var r = roles.findByName(roleName).orElseGet(() -> {
            var nr = new RoleDbEntity();     // requiere constructor vacío público/protegido
            // si NO usas @GeneratedValue para id:
            nr.setId(UUID.randomUUID());
            nr.setName(roleName);
            return roles.save(nr);
        });

        if (u.getRoles() == null) {
            u.setRoles(new HashSet<>()); // defensivo
        }
        if (u.getRoles().add(r)) {
            users.save(u);
        }
    }

    @Override
    public Set<String> getRoles(String userId) {
        var u = users.findById(UUID.fromString(userId)).orElseThrow();
        var set = u.getRoles();
        if (set == null) {
            return Set.of();
        }
        return set.stream().map(RoleDbEntity::getName).collect(Collectors.toSet());
    }

    @Override
    public boolean existsByEmailOrUsername(String emailOrUsername) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /* @Override
    public Page<User> list(String q, Boolean enabled, String role, Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }*/
    @Override
    public Page<User> list(String q, Boolean enabled, String role, Pageable pageable) {
        var spec = Specification.<UserDbEntity>where(null);

        if (q != null && !q.isBlank()) {
            var k = q.trim().toLowerCase();
            spec = spec.and((root, cq, cb) -> cb.or(
                    cb.like(cb.lower(root.get("email")), "%" + k + "%"),
                    cb.like(cb.lower(root.get("username")), "%" + k + "%")
            ));
        }
        if (enabled != null) {
            spec = spec.and((root, cq, cb) -> cb.equal(root.get("enabled"), enabled));
        }
        if (role != null && !role.isBlank()) {
            spec = spec.and((root, cq, cb) -> {
                var rolesJoin = root.join("roles"); // nombre exacto de tu @ManyToMany
                return cb.equal(rolesJoin.get("name"), role.trim());
            });
        }

        return users.findAll(spec, pageable).map(this::toDomain);
    }

    @Override
    public boolean existsEmailOrUsernameInOther(String email, String username, String excludeUserId) {
        var id = java.util.UUID.fromString(excludeUserId);
        boolean emailTaken = (email != null && !email.isBlank()) && users.existsByEmailAndIdNot(email, id);
        boolean userTaken = (username != null && !username.isBlank()) && users.existsByUsernameAndIdNot(username, id);
        return emailTaken || userTaken;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void setEnabled(String userId, boolean enabled) {
        users.updateEnabled(java.util.UUID.fromString(userId), enabled);
    }

}
