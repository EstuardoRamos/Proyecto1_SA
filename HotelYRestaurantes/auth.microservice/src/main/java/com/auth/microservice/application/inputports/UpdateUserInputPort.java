/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.application.inputports;

import com.auth.microservice.domain.User;

/**
 *
 * @author estuardoramos
 */
public interface UpdateUserInputPort {
  User update(String id, String email, String username);
}
