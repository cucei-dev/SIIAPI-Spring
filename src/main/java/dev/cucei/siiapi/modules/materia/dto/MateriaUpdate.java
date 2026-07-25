package dev.cucei.siiapi.modules.materia.dto;

/**
 * DTO for updating a materia.
 */
public record MateriaUpdate(
    String name,
    Integer creditos,
    String clave
) {
}
