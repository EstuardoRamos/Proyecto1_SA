/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.infrastructure.outputadapters;

/**
 *
 * @author estuardoramos
 */
import com.auth.microservice.application.outputports.crypto.PasswordHasherOutputPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordHasherAdapter implements PasswordHasherOutputPort {
  private final BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
  @Override public String hash(String raw){ return enc.encode(raw); }
  @Override public boolean matches(String raw, String hashed){ return enc.matches(raw, hashed); }
}