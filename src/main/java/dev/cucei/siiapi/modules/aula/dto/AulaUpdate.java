package dev.cucei.siiapi.modules.aula.dto;

/**
 * DTO for updating an aula.
 */
public record AulaUpdate(
    String name,
    Long edificioId
) {
}
