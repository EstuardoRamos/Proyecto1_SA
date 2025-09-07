// com/restaurante/microservice/facturacion/infrastructure/config/FacturaBeansConfig.java
package com.restaurante.microservice.facturacion.infraestructure.config;

import com.restaurante.microservice.consumo.application.outputports.persistence.ConsumoRepositorioPort;
import com.restaurante.microservice.cuenta.application.ouputports.persistence.CuentaRepositorioPort;
import com.restaurante.microservice.facturacion.application.inputports.*;
import com.restaurante.microservice.facturacion.application.outputports.FacturaRepositorioPort;
import com.restaurante.microservice.facturacion.application.usecases.*;
import com.restaurante.microservice.facturacion.infraestructure.outputadapters.persistence.FacturaRepositorioOutputAdapter;
import com.restaurante.microservice.facturacion.infraestructure.outputadapters.persistence.repository.FacturaRestJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FacturaBeansConfig {

    

    @Bean
    public EmitirFacturaInputPort emitirFactura(FacturaRepositorioPort facturas,
            CuentaRepositorioPort cuentas,
            ConsumoRepositorioPort consumos) {
        return new EmitirFacturaUseCase(facturas, cuentas, consumos);
    }

    @Bean
    public ObtenerFacturaInputPort obtenerFactura(FacturaRepositorioPort facturas) {
        return new ObtenerFacturaUseCase(facturas);
    }

    @Bean
    public ListarFacturasInputPort listarFacturas(FacturaRepositorioPort facturas) {
        return new ListarFacturasUseCase(facturas);
    }

    @Bean
    public AnularFacturaInputPort anularFactura(FacturaRepositorioPort facturas) {
        return new AnularFacturaUseCase(facturas);
    }
}
