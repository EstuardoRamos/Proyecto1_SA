/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.infrastructure.outputadapters.persistence.entity;

/**
 *
 * @author estuardoramos
 */
import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
@Getter
@Data
@Entity
@Table(name = "users")
public class UserDbEntity {
  @Id
  @Column(columnDefinition = "uuid")
  private UUID id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

  private boolean enabled;
  private Instant createdAt;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"),
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","role_id"})
  )
  private Set<RoleDbEntity> roles = new HashSet<>();
  
  // getters/setters...
  public Set<RoleDbEntity> getRoles() {
    if (roles == null) roles = new HashSet<>();
    return roles;
  }

  public boolean addRole(RoleDbEntity role) { return getRoles().add(role); }
  public boolean removeRole(RoleDbEntity role) { return getRoles().remove(role); }
}

