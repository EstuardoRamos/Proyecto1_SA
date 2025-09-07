/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.auth.microservice.application.outputports.persistance;

import java.util.Optional;

/**
 *
 * @author estuardoramos
 */
// PORT
public interface RoleRepositoryOutputPort {
  Optional<String> findIdByName(String name);   // opcional
  void ensureRoleExists(String name);
}
