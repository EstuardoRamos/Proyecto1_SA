/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.application.usescases;

import com.auth.microservice.application.inputports.ListUsersInputPort;
import com.auth.microservice.application.outputports.persistance.UserRepositoryOutputPort;
import com.auth.microservice.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author estuardoramos
 */
@RequiredArgsConstructor
public class ListUsersUseCase implements ListUsersInputPort {

  private final UserRepositoryOutputPort users;

  @Override
  public Page<User> list(String q, Boolean enabled, String role, Pageable pageable) {
    return users.list(q, enabled, role, pageable);
  }
}
