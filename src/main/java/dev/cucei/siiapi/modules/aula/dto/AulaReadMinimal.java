package dev.cucei.siiapi.modules.aula.dto;

/**
 * DTO for reading an aula without relationships.
 */
public record AulaReadMinimal(
    String name,
    Long edificioId,
    Long id
) {
}
