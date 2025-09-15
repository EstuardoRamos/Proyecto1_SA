// com/reportes/microservice/config/SecurityConfig.java
package com.reportes.microservice.common.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
      
        // Swagger / OpenAPI
        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
        // (Opcional) expón tus endpoints de reportes sin auth
        .requestMatchers("/v1/**").permitAll()
        
        .anyRequest().permitAll()
      )
      .httpBasic(Customizer.withDefaults())
            .cors(c -> c.configurationSource(corsSource()))
      .formLogin(form -> form.disable());
    // quita el formulario de login
    return http.build();
  }

  private CorsConfigurationSource corsSource() {
        var cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("*"));
        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setExposedHeaders(List.of("*"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}