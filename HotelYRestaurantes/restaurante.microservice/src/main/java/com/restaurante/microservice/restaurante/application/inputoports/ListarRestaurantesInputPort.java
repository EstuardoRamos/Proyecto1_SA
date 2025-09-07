package com.restaurante.microservice.restaurante.application.inputoports;

import com.restaurante.microservice.restaurante.domain.Restaurante;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarRestaurantesInputPort {

    Page<Restaurante> listar(String q, UUID hotelId, Boolean enabled, Pageable pageable);
}
