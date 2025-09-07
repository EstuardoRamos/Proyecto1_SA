package com.restaurante.microservice;

import com.restaurante.microservice.restaurante.application.inputoports.ActualizarRestauranteInputPort;
import com.restaurante.microservice.restaurante.application.inputoports.CrearRestauranteInputPort;
import com.restaurante.microservice.restaurante.application.inputoports.DeshabilitarRestauranteInputPort;
import com.restaurante.microservice.restaurante.application.inputoports.DesvincularHotelInputPort;
import com.restaurante.microservice.restaurante.application.inputoports.ListarRestaurantesInputPort;
import com.restaurante.microservice.restaurante.application.inputoports.ObtenerRestauranteInputPort;
import com.restaurante.microservice.restaurante.application.inputoports.VincularHotelInputPort;
import com.restaurante.microservice.restaurante.application.outputports.RestauranteRepositorioPort;
import com.restaurante.microservice.restaurante.application.usecases.ActualizarRestauranteUseCase;
import com.restaurante.microservice.restaurante.application.usecases.CrearRestauranteUseCase;
import com.restaurante.microservice.restaurante.application.usecases.DeshabilitarRestauranteUseCase;
import com.restaurante.microservice.restaurante.application.usecases.DesvincularHotelUseCase;
import com.restaurante.microservice.restaurante.application.usecases.ListarRestaurantesUseCase;
import com.restaurante.microservice.restaurante.application.usecases.ObtenerRestauranteUseCase;
import com.restaurante.microservice.restaurante.application.usecases.VincularHotelUseCase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages = "com.restaurante.microservice")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
                
	}
        
        @Bean
    public CrearRestauranteInputPort crearRestaurante(RestauranteRepositorioPort repo) {
        return new CrearRestauranteUseCase(repo);
    }

    @Bean
    public ActualizarRestauranteInputPort actualizarRestaurante(RestauranteRepositorioPort repo) {
        return new ActualizarRestauranteUseCase(repo);
    }

    @Bean
    public DeshabilitarRestauranteInputPort deshabilitarRestaurante(RestauranteRepositorioPort repo) {
        return new DeshabilitarRestauranteUseCase(repo);
    }

    @Bean
    public ObtenerRestauranteInputPort obtenerRestaurante(RestauranteRepositorioPort repo) {
        return new ObtenerRestauranteUseCase(repo);
    }

    @Bean
    public ListarRestaurantesInputPort listarRestaurantes(RestauranteRepositorioPort repo) {
        return (ListarRestaurantesInputPort) new ListarRestaurantesUseCase(repo);
    }

    @Bean
    public VincularHotelInputPort vincularHotel(RestauranteRepositorioPort repo) {
        return new VincularHotelUseCase(repo);
    }

    @Bean
    public DesvincularHotelInputPort desvincularHotel(RestauranteRepositorioPort repo) {
        return new DesvincularHotelUseCase(repo);
    }
    
    
    

}
