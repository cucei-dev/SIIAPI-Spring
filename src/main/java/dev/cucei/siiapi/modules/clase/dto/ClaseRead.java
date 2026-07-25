package dev.cucei.siiapi.modules.clase.dto;

import dev.cucei.siiapi.modules.aula.dto.AulaReadMinimal;
import dev.cucei.siiapi.modules.seccion.dto.SeccionReadMinimal;

import java.time.LocalTime;

/**
 * DTO for reading a clase with relationships.
 */
public record ClaseRead(
    String sesion,
    LocalTime horaInicio,
    LocalTime horaFin,
    Integer dia,
    Long seccionId,
    Long aulaId,
    Long id,
    SeccionReadMinimal seccion,
    AulaReadMinimal aula
) {
}
