package dev.cucei.siiapi.modules.edificio.dto;

/**
 * DTO for updating an edificio.
 */
public record EdificioUpdate(
    String name,
    Long centroId
) {
}
