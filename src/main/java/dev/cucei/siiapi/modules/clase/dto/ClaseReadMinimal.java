package dev.cucei.siiapi.modules.clase.dto;

import java.time.LocalTime;

/**
 * DTO for reading a clase without relationships.
 */
public record ClaseReadMinimal(
    String sesion,
    LocalTime horaInicio,
    LocalTime horaFin,
    Integer dia,
    Long seccionId,
    Long aulaId,
    Long id
) {
}
