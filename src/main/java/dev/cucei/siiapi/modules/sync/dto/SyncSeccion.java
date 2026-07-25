package dev.cucei.siiapi.modules.sync.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * DTO representing a section from SIIAU to be synced.
 */
public record SyncSeccion(
    @NotBlank String nrc,
    @NotBlank String clave,
    @NotBlank String materia,
    @NotBlank String sec,
    @Min(0) int cr,
    @Min(0) int cup,
    @Min(0) int dis,
    String est,
    String profesor,
    String periodo,
    List<SyncClase> clases
) {
}
