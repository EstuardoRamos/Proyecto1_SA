package com.auth.microservice;


import com.auth.microservice.application.inputports.AssignRoleInputPort;
import com.auth.microservice.application.inputports.GetMeInputPort;
import com.auth.microservice.application.inputports.ListUsersInputPort;
import com.auth.microservice.application.inputports.LoginInputPort;
import com.auth.microservice.application.inputports.RegisterUserInputPort;
import com.auth.microservice.application.inputports.ValidateTokenInputPort;
import com.auth.microservice.application.outputports.crypto.PasswordHasherOutputPort;
import com.auth.microservice.application.outputports.crypto.jwt.JwtProviderOutputPort;
import com.auth.microservice.application.outputports.persistance.RoleRepositoryOutputPort;
import com.auth.microservice.application.outputports.persistance.UserRepositoryOutputPort;
import com.auth.microservice.application.usescases.AuthUseCase;
import com.auth.microservice.application.usescases.ListUsersUseCase;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  @Primary // opcional pero útil
  AuthUseCase authUseCase(UserRepositoryOutputPort users,
                          RoleRepositoryOutputPort roles,
                          PasswordHasherOutputPort hasher,
                          JwtProviderOutputPort jwt,
                          @Value("${security.jwt.ttl-seconds:3600}") long ttlSeconds) {
    return new AuthUseCase(users, roles, hasher, jwt, ttlSeconds);
  }

  @Bean
  RegisterUserInputPort register(@Qualifier("authUseCase") AuthUseCase uc) { return uc; }

  @Bean
  LoginInputPort login(@Qualifier("authUseCase") AuthUseCase uc) { return uc; }

  @Bean
  GetMeInputPort me(@Qualifier("authUseCase") AuthUseCase uc) { return uc; }

  @Bean
  AssignRoleInputPort assign(@Qualifier("authUseCase") AuthUseCase uc) { return uc; }

  @Bean
  ValidateTokenInputPort validate(@Qualifier("authUseCase") AuthUseCase uc) { return uc; }


  @Bean
  ListUsersInputPort listUsers(UserRepositoryOutputPort usersRepo) {
    return new ListUsersUseCase(usersRepo);
  }

  
}
