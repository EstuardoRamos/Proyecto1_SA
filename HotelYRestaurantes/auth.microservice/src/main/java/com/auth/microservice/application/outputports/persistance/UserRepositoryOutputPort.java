/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.auth.microservice.application.outputports.persistance;

import com.auth.microservice.domain.User;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author estuardoramos
 */


/** El id viaja como UUID en texto (String). */
public interface UserRepositoryOutputPort {
  Optional<User> findByEmailOrUsername(String emailOrUsername);
  Optional<User> findById(String id);
  boolean existsByEmailOrUsername(String emailOrUsername);

  User save(User u);

  void addRole(String userId, String roleName);  // idempotente
  Set<String> getRoles(String userId);
  
  Page<User> list(String q, Boolean enabled, String role, Pageable pageable);
  

  boolean existsEmailOrUsernameInOther(String email, String username, String excludeUserId);
  void setEnabled(String userId, boolean enabled);
}
