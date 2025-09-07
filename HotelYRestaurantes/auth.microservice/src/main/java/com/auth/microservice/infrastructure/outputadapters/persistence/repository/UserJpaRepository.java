/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.infrastructure.outputadapters.persistence.repository;

/**
 *
 * @author estuardoramos
 */
import com.auth.microservice.infrastructure.outputadapters.persistence.entity.UserDbEntity;
import com.auth.microservice.infrastructure.outputadapters.persistence.entity.UserDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepository
    extends JpaRepository<UserDbEntity, UUID>,
            JpaSpecificationExecutor<UserDbEntity> { // ⬅️ necesario para findAll(spec, pageable)

  @Query("select u from UserDbEntity u where u.email = :q or u.username = :q")
  Optional<UserDbEntity> findByEmailOrUsername(@Param("q") String q);

  @Query("select (count(u) > 0) from UserDbEntity u where u.email = :q or u.username = :q")
  boolean existsByEmailOrUsername(@Param("q") String q);
  
  @Query("select (count(u) > 0) from UserDbEntity u where lower(u.email) = lower(:email) and u.id <> :id")
  boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") UUID id);

  @Query("select (count(u) > 0) from UserDbEntity u where lower(u.username) = lower(:username) and u.id <> :id")
  boolean existsByUsernameAndIdNot(@Param("username") String username, @Param("id") UUID id);

  @Modifying
  @Query("update UserDbEntity u set u.enabled = :enabled where u.id = :id")
  int updateEnabled(@Param("id") UUID id, @Param("enabled") boolean enabled);
}
