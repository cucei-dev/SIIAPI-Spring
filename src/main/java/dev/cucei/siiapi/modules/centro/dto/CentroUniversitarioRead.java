package dev.cucei.siiapi.modules.centro.dto;

import dev.cucei.siiapi.modules.edificio.dto.EdificioReadMinimal;
import dev.cucei.siiapi.modules.seccion.dto.SeccionReadMinimal;
import java.util.List;

/**
 * DTO for reading a centro universitario with relationships.
 */
public record CentroUniversitarioRead(
    String name,
    String siiauId,
    Long id,
    List<SeccionReadMinimal> secciones,
    List<EdificioReadMinimal> edificios
) {
}
