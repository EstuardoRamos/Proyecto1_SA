/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.auth.microservice.application.outputports.crypto.jwt;

import java.util.Map;

/**
 *
 * @author estuardoramos
 */
public interface JwtProviderOutputPort {

    String generate(String subject, Map<String, Object> claims, long ttlSeconds);

    Map<String, Object> parse(String token); // lanza excepción si inválido/expirado

}
