package dev.cucei.siiapi.modules.centro.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for creating a centro universitario.
 */
public record CentroUniversitarioCreate(
    @NotBlank String name,
    @NotBlank String siiauId
) {
}
