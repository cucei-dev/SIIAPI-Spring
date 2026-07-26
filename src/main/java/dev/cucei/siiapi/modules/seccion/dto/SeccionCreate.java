package dev.cucei.siiapi.modules.seccion.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO for creating a seccion.
 */
public record SeccionCreate(
    @NotBlank String name,
    @NotBlank String nrc,
    @Min(0) int cupos,
    int cuposDisponibles,
    String est,
    LocalDateTime periodoInicio,
    LocalDateTime periodoFin,
    @NotNull Long centroId,
    @NotNull Long materiaId,
    Long profesorId,
    @NotNull Long calendarioId
) {
}
