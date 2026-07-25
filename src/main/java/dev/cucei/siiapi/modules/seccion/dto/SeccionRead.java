package dev.cucei.siiapi.modules.seccion.dto;

import dev.cucei.siiapi.modules.calendario.dto.CalendarioReadMinimal;
import dev.cucei.siiapi.modules.centro.dto.CentroUniversitarioReadMinimal;
import dev.cucei.siiapi.modules.clase.dto.ClaseReadMinimal;
import dev.cucei.siiapi.modules.materia.dto.MateriaReadMinimal;
import dev.cucei.siiapi.modules.profesor.dto.ProfesorReadMinimal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for reading a seccion with all relationships.
 */
public record SeccionRead(
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
    Long id,
    CentroUniversitarioReadMinimal centro,
    MateriaReadMinimal materia,
    ProfesorReadMinimal profesor,
    CalendarioReadMinimal calendario,
    List<ClaseReadMinimal> clases
) {
}
