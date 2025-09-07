/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.application.inputports;

import java.util.Map;

/**
 *
 * @author estuardoramos
 */
public interface ValidateTokenInputPort {

    Map<String, Object> validate(String token);
}
