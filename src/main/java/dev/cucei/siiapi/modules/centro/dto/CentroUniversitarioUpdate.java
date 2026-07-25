package dev.cucei.siiapi.modules.centro.dto;

/**
 * DTO for updating a centro universitario.
 */
public record CentroUniversitarioUpdate(
    String name,
    String siiauId
) {
}
