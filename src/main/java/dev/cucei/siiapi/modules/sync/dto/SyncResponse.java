package dev.cucei.siiapi.modules.sync.dto;

/**
 * DTO for the sync response with statistics.
 */
public record SyncResponse(
    long seccionesCreadas,
    long seccionesActualizadas,
    long seccionesEliminadas,
    long clasesCreadas,
    long clasesActualizadas,
    long clasesEliminadas,
    long materiasCreadas,
    long profesoresCreados,
    long edificiosCreados,
    long aulasCreadas
) {
}
