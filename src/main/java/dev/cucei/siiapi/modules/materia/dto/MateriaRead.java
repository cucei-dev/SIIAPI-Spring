package dev.cucei.siiapi.modules.materia.dto;

import dev.cucei.siiapi.modules.seccion.dto.SeccionReadMinimal;
import java.util.List;

/**
 * DTO for reading a materia with its secciones.
 */
public record MateriaRead(
    String name,
    int creditos,
    String clave,
    Long id,
    List<SeccionReadMinimal> secciones
) {
}
