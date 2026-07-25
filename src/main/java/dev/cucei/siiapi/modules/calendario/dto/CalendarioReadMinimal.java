package dev.cucei.siiapi.modules.calendario.dto;

/**
 * DTO for reading a calendario without relationships.
 */
public record CalendarioReadMinimal(
    String name,
    String siiauId,
    Long id
) {
}
