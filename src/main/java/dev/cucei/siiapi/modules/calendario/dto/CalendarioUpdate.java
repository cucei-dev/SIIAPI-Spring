package dev.cucei.siiapi.modules.calendario.dto;

/**
 * DTO for updating a calendario. All fields are optional.
 */
public record CalendarioUpdate(
    String name,
    String siiauId
) {
}
