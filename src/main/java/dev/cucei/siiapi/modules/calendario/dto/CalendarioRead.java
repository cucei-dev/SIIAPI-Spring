package dev.cucei.siiapi.modules.calendario.dto;

import dev.cucei.siiapi.modules.seccion.dto.SeccionReadMinimal;
import java.util.List;

/**
 * DTO for reading a calendario with its secciones.
 */
public record CalendarioRead(
    String name,
    String siiauId,
    Long id,
    List<SeccionReadMinimal> secciones
) {
}
