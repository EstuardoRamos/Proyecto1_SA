/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.infrastructure.inputadapters.rest;

import com.auth.microservice.application.inputports.EnableDisableUserInputPort;
import com.auth.microservice.application.inputports.UpdateUserInputPort;
import com.auth.microservice.application.outputports.persistance.UserRepositoryOutputPort;
import com.auth.microservice.domain.User;
import lombok.RequiredArgsConstructor;
//import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import com.auth.microservice.shared.errors.NotFoundException;
import com.auth.microservice.shared.errors.AlreadyExistsException;


/**
 *
 * @author estuardoramos
 */
@Service
@RequiredArgsConstructor
public class ManageUserUseCase implements UpdateUserInputPort, EnableDisableUserInputPort {

  private final UserRepositoryOutputPort users;

  @Override
  public User update(String id, String email, String username) {
    var current = users.findById(id)
        .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

    // validar duplicados en otros usuarios
    if (users.existsEmailOrUsernameInOther(email, username, id)) {
      throw new AlreadyExistsException("Email o username ya está en uso");
    }

    // construir una nueva instancia con cambios (inmutable-friendly)
    var updated = new com.auth.microservice.domain.User(
        current.getId(),                                     // UUID
        (email == null || email.isBlank()) ? current.getEmail() : email.trim(),
        (username == null || username.isBlank()) ? current.getUsername() : username.trim(),
        current.getPasswordHash(),
        current.isEnabled(),
        current.getCreatedAt(),
        current.getRoles(),
        current.getFailedLoginAttempts()
    );

    return users.save(updated);
  }

  @Override
  public void setEnabled(String id, boolean enabled) {
    // lanza 404 si no existe
    users.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    users.setEnabled(id, enabled);
  }
}

