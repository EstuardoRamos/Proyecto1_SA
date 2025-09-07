/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.application.usescases;

/**
 *
 * @author estuardoramos
 */
import com.auth.microservice.application.inputports.AssignRoleInputPort;
import com.auth.microservice.application.inputports.GetMeInputPort;
import com.auth.microservice.application.inputports.LoginInputPort;
import com.auth.microservice.application.inputports.RegisterUserInputPort;
import com.auth.microservice.application.inputports.ValidateTokenInputPort;
import com.auth.microservice.application.outputports.crypto.PasswordHasherOutputPort;
import com.auth.microservice.application.outputports.crypto.jwt.JwtProviderOutputPort;
import com.auth.microservice.application.outputports.persistance.RoleRepositoryOutputPort;
import com.auth.microservice.application.outputports.persistance.UserRepositoryOutputPort;
import com.auth.microservice.domain.User;
import java.time.Instant;
import java.util.*;

public class AuthUseCase implements
        RegisterUserInputPort, LoginInputPort, GetMeInputPort, AssignRoleInputPort, ValidateTokenInputPort {

    private final UserRepositoryOutputPort users;
    private final RoleRepositoryOutputPort roles;
    //private final PasswordHasherOutputPort hasher;
    private final PasswordHasherOutputPort hasher;
    private final JwtProviderOutputPort jwt;
    private final long ttlSeconds;

    public AuthUseCase(UserRepositoryOutputPort users, RoleRepositoryOutputPort roles,
            PasswordHasherOutputPort hasher, JwtProviderOutputPort jwt, long ttlSeconds) {
        this.users = users;
        this.roles = roles;
        this.hasher = hasher;
        this.jwt = jwt;
        this.ttlSeconds = ttlSeconds;
    }

    @Override
    public User register(String email, String username, String password) {
        var u = new User(email, username, hasher.hash(password)); // genera UUID, enabled=true, roles vacíos
        var saved = users.save(u);
        roles.ensureRoleExists("ROLE_USER");
        users.addRole(String.valueOf(saved.getId()), "ROLE_USER"); // <- usa getter porque es @Getter, no record
        return saved;
    }

    @Override
    public String login(String emailOrUser, String password) {
        var u = users.findByEmailOrUsername(emailOrUser)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!hasher.matches(password, u.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        var userId = u.getId().toString(); // ← UUID → String
        var userRoles = users.getRoles(userId);

        var claims = Map.<String, Object>of(
                "email", u.getEmail(),
                "username", u.getUsername(),
                "roles", userRoles
        );

        return jwt.generate(userId, claims, ttlSeconds); // ← pasa String
    }

    @Override
    public User me(String token) {
        var claims = jwt.parse(token);
        var userId = (String) claims.get("sub");
        return users.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void assign(String userId, String roleName) {
        roles.ensureRoleExists(roleName);
        users.addRole(userId, roleName);
    }

    @Override
    public Map<String, Object> validate(String token) {
        return jwt.parse(token);
    }
}
