package dev.cucei.siiapi.modules.seccion.dto;

import java.time.LocalDateTime;

/**
 * DTO for reading a seccion without relationships.
 */
public record SeccionReadMinimal(
    String name,
    String nrc,
    int cupos,
    int cuposDisponibles,
    String est,
    LocalDateTime periodoInicio,
    LocalDateTime periodoFin,
    Long centroId,
    Long materiaId,
    Long profesorId,
    Long calendarioId,
    Long id
) {
}
