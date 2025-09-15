package com.reviews.microservice.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration c = new CorsConfiguration();
    // ¡Un solo origen, no '*' si usas credenciales!
    c.setAllowedOrigins(List.of("http://localhost:4200"));
    c.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
    c.setAllowedHeaders(List.of("*"));          // acepta cualquier header (incl. Authorization, Content-Type)
    c.setAllowCredentials(true);
    c.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
    // Aplica a todas tus APIs (ajusta si quieres más fino)
    src.registerCorsConfiguration("/v1/**", c);
    return src;
  }
}