// user/infrastructure/inputadapters/rest/dto/DTOs.java
package com.user.microservice.user.infrastructure.inputadapters.rest.dto;

import com.user.microservice.user.domain.*;
import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.UUID;

public record RegistrarRequest(
        @NotBlank
        String nombre,
        @Email
        @NotBlank
        String email,
        @Size(min = 6)
        String password,
        @NotBlank
        String dpi,
        Rol rol
        ) {

}

