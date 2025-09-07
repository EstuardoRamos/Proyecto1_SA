/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.application.inputports;

import com.auth.microservice.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 *
 * @author estuardoramos
 */
public interface ListUsersInputPort {
  Page<User> list(String q, Boolean enabled, String role, Pageable pageable);
}
