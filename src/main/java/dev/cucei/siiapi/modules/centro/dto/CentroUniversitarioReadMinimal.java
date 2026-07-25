package dev.cucei.siiapi.modules.centro.dto;

/**
 * DTO for reading a centro universitario without relationships.
 */
public record CentroUniversitarioReadMinimal(
    String name,
    String siiauId,
    Long id
) {
}
