package com.restaurante.microservice.consumo.infrastructure.config;

// infrastrucuture/config/ConsumoBeansConfig.java
import com.restaurante.microservice.consumo.application.inputports.ActualizarConsumoInputPort;
import com.restaurante.microservice.consumo.application.inputports.CrearConsumoInputPort;
import com.restaurante.microservice.consumo.application.inputports.EliminarConsumoInputPort;
import com.restaurante.microservice.consumo.application.inputports.ListarConsumosInputPort;
import com.restaurante.microservice.consumo.application.inputports.ObtenerConsumoInputPort;
import com.restaurante.microservice.consumo.application.outputports.persistence.ConsumoRepositorioPort;
import com.restaurante.microservice.consumo.application.outputports.query.RestauranteConsultaPort;
import com.restaurante.microservice.consumo.application.usecases.*;
import com.restaurante.microservice.consumo.infrastructure.outputadapters.persistence.ConsumoRepositorioOutputAdapter;
import com.restaurante.microservice.consumo.infrastructure.outputadapters.persistence.repository.ConsumoJpaRepository;
import com.restaurante.microservice.consumo.infrastructure.outputadapters.query.RestauranteConsultaAdapter;
import com.restaurante.microservice.cuenta.application.ouputports.persistence.CuentaRepositorioPort;
import com.restaurante.microservice.restaurante.infrastrucuture.outputadapters.persistence.respository.RestauranteJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumoBeansConfig {

    @Bean
    public ConsumoRepositorioPort consumoRepoPort(ConsumoJpaRepository jpa) {
        return new ConsumoRepositorioOutputAdapter(jpa);
    }

    @Bean
    public RestauranteConsultaPort restauranteConsultaPort(
            RestauranteJpaRepository restRepo) {
        return new RestauranteConsultaAdapter(restRepo);
    }

    @Bean
    public CrearConsumoInputPort crearConsumo(ConsumoRepositorioPort consumoRepo,
            CuentaRepositorioPort cuentaRepo,
            RestauranteConsultaPort restCfg) {
        return new CrearConsumoUseCase(consumoRepo, cuentaRepo, restCfg);
    }

    @Bean
    public ActualizarConsumoInputPort actualizarConsumo(ConsumoRepositorioPort consumoRepo,
            CuentaRepositorioPort cuentaRepo,
            RestauranteConsultaPort restCfg) {
        return new ActualizarConsumoUseCase(consumoRepo, cuentaRepo, restCfg);
    }

    @Bean
    public EliminarConsumoInputPort eliminarConsumo(ConsumoRepositorioPort consumoRepo,
            CuentaRepositorioPort cuentaRepo,
            RestauranteConsultaPort restCfg) {
        return new EliminarConsumoUseCase(consumoRepo, cuentaRepo, restCfg);
    }

    @Bean
    public ObtenerConsumoInputPort obtenerConsumo(ConsumoRepositorioPort consumoRepo) {
        return new ObtenerConsumoUseCase(consumoRepo);
    }

    @Bean
    public ListarConsumosInputPort listarConsumos(ConsumoRepositorioPort consumoRepo) {
        return new ListarConsumosUseCase(consumoRepo);
    }
}
