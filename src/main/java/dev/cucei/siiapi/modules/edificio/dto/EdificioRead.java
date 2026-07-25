package dev.cucei.siiapi.modules.edificio.dto;

import dev.cucei.siiapi.modules.aula.dto.AulaReadMinimal;
import dev.cucei.siiapi.modules.centro.dto.CentroUniversitarioReadMinimal;
import java.util.List;

/**
 * DTO for reading an edificio with relationships.
 */
public record EdificioRead(
    String name,
    Long centroId,
    Long id,
    CentroUniversitarioReadMinimal centro,
    List<AulaReadMinimal> aulas
) {
}
