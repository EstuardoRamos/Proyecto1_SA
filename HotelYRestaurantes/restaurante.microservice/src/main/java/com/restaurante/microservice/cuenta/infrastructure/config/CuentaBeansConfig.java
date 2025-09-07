package com.restaurante.microservice.cuenta.infrastructure.config;

import com.restaurante.microservice.cuenta.application.inputports.AbrirCuentaInputPort;
import com.restaurante.microservice.cuenta.application.inputports.CerrarCuentaInputPort;
import com.restaurante.microservice.cuenta.application.inputports.CobrarCuentaInputPort;
import com.restaurante.microservice.cuenta.application.inputports.ListarCuentasInputPort;
import com.restaurante.microservice.cuenta.application.inputports.ObtenerCuentaInputPort;
import com.restaurante.microservice.cuenta.application.ouputports.persistence.CuentaRepositorioPort;
import com.restaurante.microservice.cuenta.application.usecases.*;
import com.restaurante.microservice.cuenta.infrastructure.outputadapters.persistence.CuentaRepositorioOutputAdapter;
import com.restaurante.microservice.cuenta.infrastructure.outputadapters.persistence.repository.CuentaJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CuentaBeansConfig {

   

    @Bean
    public AbrirCuentaInputPort abrirCuenta(CuentaRepositorioPort repo) {
        return new AbrirCuentaUseCase(repo);
    }

    @Bean
    public ObtenerCuentaInputPort obtenerCuenta(CuentaRepositorioPort repo) {
        return new ObtenerCuentaUseCase(repo);
    }

    @Bean
    public ListarCuentasInputPort listarCuentas(CuentaRepositorioPort repo) {
        return new ListarCuentasUseCase(repo);
    }

    @Bean
    public CerrarCuentaInputPort cerrarCuenta(CuentaRepositorioPort repo) {
        return new CerrarCuentaUseCase(repo);
    }

    @Bean
    public CobrarCuentaInputPort cobrarCuenta(CuentaRepositorioPort repo) {
        return new CobrarCuentaUseCase(repo);
    }
}
