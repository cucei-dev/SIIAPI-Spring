package dev.cucei.siiapi.modules.aula.dto;

import dev.cucei.siiapi.modules.clase.dto.ClaseReadMinimal;
import dev.cucei.siiapi.modules.edificio.dto.EdificioReadMinimal;
import java.util.List;

/**
 * DTO for reading an aula with relationships.
 */
public record AulaRead(
    String name,
    Long edificioId,
    Long id,
    EdificioReadMinimal edificio,
    List<ClaseReadMinimal> clases
) {
}
