package dev.cucei.siiapi.modules.profesor.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for creating a profesor.
 */
public record ProfesorCreate(
    @NotBlank String name
) {
}
