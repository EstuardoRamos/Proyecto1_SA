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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.cors.*;

// reviews.microservice - SecurityConfig.java
// SecurityConfig.java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  // IMPORTANTE: usa el nombre estándar del bean
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration cfg = new CorsConfiguration();

    // Usa allowedOrigins (coincidencia exacta) para tu dominio de S3
    cfg.setAllowedOrigins(List.of(
      "http://frontend-comerdormir.s3-website.us-east-2.amazonaws.com"
      // si llegas a servir por HTTPS añade también:
      // "https://frontend-comerdormir.s3-website.us-east-2.amazonaws.com"
    ));

    // Si NO usas cookies/sesión, no actives credentials.
    // Si algún día usas cookies, activa y quita comodines.
    // cfg.setAllowCredentials(true);

    cfg.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
    cfg.setAllowedHeaders(List.of("*"));
    cfg.setExposedHeaders(List.of("*"));
    cfg.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);
    return source;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .csrf(csrf -> csrf.disable())
      // usa el bean "corsConfigurationSource" anterior
      .cors(Customizer.withDefaults())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .requestMatchers("/actuator/**","/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html","/error").permitAll()
        .requestMatchers(HttpMethod.POST, "/v1/reviews/hotel", "/v1/reviews/platillos").permitAll()
        .requestMatchers(HttpMethod.GET, "/v1/reviews/**").permitAll()
        .anyRequest().authenticated()
      )
      .build();
  }
}
