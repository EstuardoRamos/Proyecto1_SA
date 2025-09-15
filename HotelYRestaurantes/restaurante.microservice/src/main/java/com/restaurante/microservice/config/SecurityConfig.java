package com.restaurante.microservice.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        // 🔗 Activa CORS y usa el bean de abajo
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/v3/api-docs/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/actuator/health"
            ).permitAll()
            .anyRequest().permitAll() // en prod probablemente .authenticated()
        );

    return http.build();
  }

  // 👉 Exponer CORS como @Bean
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration cfg = new CorsConfiguration();

    // --- Modo DEV (orígenes abiertos). Usa allowedOriginPatterns con "*"
    //     Si vas a usar credenciales (cookies/autorización), NO puedes usar "*"
    cfg.setAllowedOriginPatterns(List.of("*"));   // o usa setAllowedOrigins(List.of("http://localhost:4200"))
    cfg.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
    cfg.setAllowedHeaders(List.of("*"));
    cfg.setExposedHeaders(List.of("*"));
    cfg.setAllowCredentials(false);               // true solo si confías en el/los origen/es específicos
    cfg.setMaxAge(3600L);                         // cache del preflight (OPTIONS) en el navegador

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);
    return source;
  }
}
