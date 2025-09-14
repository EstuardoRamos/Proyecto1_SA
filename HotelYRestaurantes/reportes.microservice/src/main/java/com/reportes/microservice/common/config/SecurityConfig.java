// com/reportes/microservice/config/SecurityConfig.java
package com.reportes.microservice.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
      .formLogin(form -> form.disable()); // quita el formulario de login
    return http.build();
  }
}