// infrastructure/config/FacturaHotelBeansConfig.java
package com.hotel.microservice.facturacion.infrastructure.config;

import com.hotel.microservice.facturacion.application.inputports.*;
import com.hotel.microservice.facturacion.application.outputports.persistence.FacturaHotelRepositorioPort;
import com.hotel.microservice.facturacion.application.outputports.query.ReservaHotelQueryPort;
import com.hotel.microservice.facturacion.application.usecases.*;
import com.hotel.microservice.facturacion.infrastructure.outputadapters.persistence.FacturaHotelRepositorioOutputAdapter;
import com.hotel.microservice.facturacion.infrastructure.outputadapters.persistence.repository.FacturaHotelJpaRepository;
import com.hotel.microservice.reserva.application.inputports.CheckOutInputPort;
import com.hotel.microservice.reserva.application.outputports.ReservaRepositorioPort;
import com.hotel.microservice.reserva.application.usecases.CheckOutUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FacturaHotelBeansConfig {

    //@Bean
    //public FacturaHotelRepositorioPort facturaHotelRepo(FacturaHotelJpaRepository jpa) {
    //  return new FacturaHotelRepositorioOutputAdapter(jpa);
    //}
    // OJO: necesitas un bean que implemente ReservaHotelQueryPort (adaptador a tu módulo de Reservas)
    @Bean
    public EmitirFacturaHotelInputPort emitirFacturaHotel(FacturaHotelRepositorioPort repo,
            ReservaHotelQueryPort reservas) {
        return new EmitirFacturaHotelUseCase(repo, reservas);
    }

    @Bean
    public ObtenerFacturaHotelInputPort obtenerFacturaHotel(FacturaHotelRepositorioPort repo) {
        return new ObtenerFacturaHotelUseCase(repo);
    }

    @Bean
    public ListarFacturasHotelInputPort listarFacturasHotel(FacturaHotelRepositorioPort repo) {
        return new ListarFacturasHotelUseCase(repo);
    }

    @Bean
    public AnularFacturaHotelInputPort anularFacturaHotel(FacturaHotelRepositorioPort repo) {
        return new AnularFacturaHotelUseCase(repo);
    }

   
}
