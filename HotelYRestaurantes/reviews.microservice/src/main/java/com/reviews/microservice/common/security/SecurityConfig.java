// com.reviews.microservice.common.security.SecurityConfig
package com.reviews.microservice.common.security;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;

// reviews.microservice - SecurityConfig.java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .csrf(csrf -> csrf.disable()) // <- importante para POST desde Postman/Front
      .cors(Customizer.withDefaults())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/actuator/**","/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html","/error").permitAll()
        .requestMatchers(HttpMethod.POST, "/v1/reviews/hotel", "/v1/reviews/platillos").permitAll()
        .requestMatchers(HttpMethod.GET,  "/v1/reviews/**").permitAll()
        .anyRequest().authenticated()
      )
      .build();
  }
}