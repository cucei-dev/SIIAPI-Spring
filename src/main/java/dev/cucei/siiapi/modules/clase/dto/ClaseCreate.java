package dev.cucei.siiapi.modules.clase.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

/**
 * DTO for creating a clase.
 */
public record ClaseCreate(
    String sesion,
    LocalTime horaInicio,
    LocalTime horaFin,
    Integer dia,
    @NotNull Long seccionId,
    Long aulaId
) {
}
