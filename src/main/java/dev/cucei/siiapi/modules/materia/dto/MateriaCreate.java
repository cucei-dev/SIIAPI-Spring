package dev.cucei.siiapi.modules.materia.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for creating a materia.
 */
public record MateriaCreate(
    @NotBlank String name,
    @Min(0) int creditos,
    @NotBlank String clave
) {
}
