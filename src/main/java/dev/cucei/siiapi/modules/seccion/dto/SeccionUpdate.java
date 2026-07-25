package dev.cucei.siiapi.modules.seccion.dto;

import java.time.LocalDateTime;

/**
 * DTO for updating a seccion. All fields are optional.
 */
public record SeccionUpdate(
    String name,
    String nrc,
    Integer cupos,
    Integer cuposDisponibles,
    String est,
    LocalDateTime periodoInicio,
    LocalDateTime periodoFin,
    Long centroId,
    Long materiaId,
    Long profesorId,
    Long calendarioId
) {
}
