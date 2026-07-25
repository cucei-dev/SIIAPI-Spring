package dev.cucei.siiapi.modules.profesor.dto;

/**
 * DTO for reading a profesor without relationships.
 */
public record ProfesorReadMinimal(
    String name,
    Long id
) {
}
