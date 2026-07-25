package dev.cucei.siiapi.modules.clase.dto;

import java.time.LocalTime;

/**
 * DTO for updating a clase.
 */
public record ClaseUpdate(
    String sesion,
    LocalTime horaInicio,
    LocalTime horaFin,
    Integer dia,
    Long seccionId,
    Long aulaId
) {
}
