package dev.cucei.siiapi.modules.aula.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating an aula.
 */
public record AulaCreate(
    @NotBlank String name,
    @NotNull Long edificioId
) {
}
