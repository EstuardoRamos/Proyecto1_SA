/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.infrastructure.inputadapters.rest.dto;

/**
 *
 * @author estuardoramos
 */
import jakarta.validation.constraints.*;

public record RegisterRequest(
        @Email
        @NotBlank
        String email, @NotBlank
        String username, @NotBlank
        String password) {

}
