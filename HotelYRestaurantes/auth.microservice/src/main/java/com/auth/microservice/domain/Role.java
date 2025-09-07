/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.domain;

import java.util.UUID;
import lombok.Getter;

/**
 *
 * @author estuardoramos
 */

@Getter
public class Role {
    private UUID id;
    private String name;

    public Role(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
