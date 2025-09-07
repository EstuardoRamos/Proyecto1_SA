/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.auth.microservice.application.inputports;

/**
 *
 * @author estuardoramos
 */
public interface LoginInputPort {
    String login(String emailOrUser, String password);
    
}
