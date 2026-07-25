package dev.cucei.siiapi.modules.calendario.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for creating a calendario.
 */
public record CalendarioCreate(
    @NotBlank String name,
    @NotBlank String siiauId
) {
}
