package dev.cucei.siiapi.modules.edificio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating an edificio.
 */
public record EdificioCreate(
    @NotBlank String name,
    @NotNull Long centroId
) {
}
