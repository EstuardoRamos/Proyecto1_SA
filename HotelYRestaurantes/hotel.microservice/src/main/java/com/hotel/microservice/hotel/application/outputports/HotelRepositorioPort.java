package com.hotel.microservice.hotel.application.outputports;

import com.hotel.microservice.hotel.domain.Hotel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HotelRepositorioPort {
  boolean existePorNombre(String nombre);
  Hotel guardar(Hotel hotel);
  Optional<Hotel> buscarPorId(UUID id);
  List<Hotel> listarActivos();
  void desactivar(UUID id);
}