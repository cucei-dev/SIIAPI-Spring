package dev.cucei.siiapi.modules.profesor.dto;

import dev.cucei.siiapi.modules.seccion.dto.SeccionReadMinimal;
import java.util.List;

/**
 * DTO for reading a profesor with its secciones.
 */
public record ProfesorRead(
    String name,
    Long id,
    List<SeccionReadMinimal> secciones
) {
}
