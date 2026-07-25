package dev.cucei.siiapi.modules.edificio.dto;

/**
 * DTO for reading an edificio without relationships.
 */
public record EdificioReadMinimal(
    String name,
    Long centroId,
    Long id
) {
}
