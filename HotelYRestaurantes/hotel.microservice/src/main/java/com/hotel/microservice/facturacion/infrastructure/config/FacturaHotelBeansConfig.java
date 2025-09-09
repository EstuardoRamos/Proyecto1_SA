// infrastructure/config/FacturaHotelBeansConfig.java
package com.hotel.microservice.facturacion.infrastructure.config;

import com.hotel.microservice.facturacion.application.inputports.*;
import com.hotel.microservice.facturacion.application.outputports.persistence.FacturaHotelRepositorioPort;
import com.hotel.microservice.facturacion.application.outputports.query.ReservaHotelQueryPort;
import com.hotel.microservice.facturacion.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FacturaHotelBeansConfig {

    //@Bean
    //public FacturaHotelRepositorioPort facturaHotelRepo(FacturaHotelJpaRepository jpa) {
    //  return new FacturaHotelRepositorioOutputAdapter(jpa);
    //}
    // OJO: necesitas un bean que implemente ReservaHotelQueryPort (adaptador a tu módulo de Reservas)
    

   
}
