package com.restaurante.microservice.restaurante.infrastrucuture.inputadapters.rest.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class VincularHotelRequest {
  @NotNull private UUID hotelId;
  public UUID getHotelId() { return hotelId; }
  public void setHotelId(UUID hotelId) { this.hotelId = hotelId; }
}
