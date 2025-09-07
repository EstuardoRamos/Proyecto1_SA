/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.infrastructure.inputadapters.rest.dto;

/**
 *
 * @author estuardoramos
 */
import com.auth.microservice.application.inputports.*;
import com.auth.microservice.infrastructure.inputadapters.rest.dto.MeResponse;
import com.auth.microservice.infrastructure.inputadapters.rest.dto.*;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthControllerAdapter {

    private final RegisterUserInputPort register;
    private final LoginInputPort login;
    private final GetMeInputPort me;
    private final AssignRoleInputPort assign;
    private final ValidateTokenInputPort validate;
    private final ListUsersInputPort listUsers;
    private final UpdateUserInputPort updateUser;
    private final EnableDisableUserInputPort enableDisableUser;

    public AuthControllerAdapter(RegisterUserInputPort register, LoginInputPort login, GetMeInputPort me,
            AssignRoleInputPort assign, ValidateTokenInputPort validate, ListUsersInputPort listUsers, UpdateUserInputPort updateUser, EnableDisableUserInputPort enableDisableUser) {
        this.register = register;
        this.login = login;
        this.me = me;
        this.assign = assign;
        this.validate = validate;
        this.listUsers = listUsers;
        this.updateUser = updateUser;
        this.enableDisableUser = enableDisableUser;
    }

    @PostMapping("/register")
    public MeResponse register(@Valid @RequestBody RegisterRequest req) {
        var u = register.register(req.email(), req.username(), req.password());
        return new MeResponse(String.valueOf(u.getId()), u.getEmail(), u.getUsername(), u.isEnabled(), u.getCreatedAt(), u.getRoles());
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest req) {
        var token = login.login(req.usernameOrEmail(), req.password());
        return new TokenResponse(token, "Bearer", 3600);
    }

    @GetMapping("/me")
    public MeResponse me(@RequestHeader("Authorization") String authorization) {
        var u = me.me(authorization);
        return new MeResponse(String.valueOf(u.getId()), u.getEmail(), u.getUsername(), u.isEnabled(), u.getCreatedAt(), u.getRoles());
    }

    @PostMapping("/assign-role")
    public void assign(@RequestParam String userId, @RequestParam String role) {
        assign.assign(userId, role);
    }

    @PostMapping("/validate")
    public Object validate(@RequestHeader("Authorization") String authorization) {
        return validate.validate(authorization);
    }

    @GetMapping("/users")
    public List<UserSummaryResponse> listUsers(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) String role) {

        // sin paginación: trae todos (usa Specifications/@Query que ya tienes)
        var page = listUsers.list(q, enabled, role, org.springframework.data.domain.Pageable.unpaged());

        return page.getContent().stream()
                .map(u -> new UserSummaryResponse(
                u.getId().toString(),
                u.getEmail(),
                u.getUsername(),
                u.isEnabled(),
                u.getCreatedAt(),
                u.getRoles()))
                .toList();
    }

    private Pageable toPageable(String sort, int page, int size) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String s : sort.split(";")) {
            String[] parts = s.split(",", 2);
            String prop = parts[0].trim();
            Sort.Direction dir = (parts.length > 1 && "desc".equalsIgnoreCase(parts[1].trim()))
                    ? Sort.Direction.DESC : Sort.Direction.ASC;
            orders.add(new Sort.Order(dir, prop));
        }
        int p = Math.max(0, page);
        int sz = Math.min(Math.max(1, size), 100);
        return PageRequest.of(p, sz, Sort.by(orders));
    }

    // DTOs
    public record UserSummaryResponse(
            String id, String email, String username, boolean enabled,
            Instant createdAt, Set<String> roles) {

    }

    public record PageResponse<T>(
            List<T> content, int page, int size,
            long totalElements, int totalPages, boolean first, boolean last) {

    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}")
    public UserSummaryResponse updateUser(
            @PathVariable String id,
            @RequestBody UpdateUserRequest body) {

        var u = updateUser.update(id, body.email(), body.username());
        return new UserSummaryResponse(
                u.getId().toString(), u.getEmail(), u.getUsername(),
                u.isEnabled(), u.getCreatedAt(), u.getRoles());
    }

    // DELETE = deshabilitar
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> disableUser(@PathVariable String id) {
        enableDisableUser.disable(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH para habilitar/deshabilitar explícito
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/users/{id}/enabled")
    public ResponseEntity<Void> setEnabled(
            @PathVariable String id,
            @RequestBody ToggleEnabledRequest body) {
        enableDisableUser.setEnabled(id, body.enabled());
        return ResponseEntity.noContent().build();
    }
}
