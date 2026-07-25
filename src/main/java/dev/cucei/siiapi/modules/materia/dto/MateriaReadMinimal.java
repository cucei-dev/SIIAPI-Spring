package dev.cucei.siiapi.modules.materia.dto;

/**
 * DTO for reading a materia without relationships.
 */
public record MateriaReadMinimal(
    String name,
    int creditos,
    String clave,
    Long id
) {
}
