/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.application.inputports;

/**
 *
 * @author estuardoramos
 */
public interface EnableDisableUserInputPort {
  void setEnabled(String id, boolean enabled);
  default void disable(String id) { setEnabled(id, false); }
  default void enable(String id)  { setEnabled(id, true);  }
}