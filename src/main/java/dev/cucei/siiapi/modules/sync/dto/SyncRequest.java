package dev.cucei.siiapi.modules.sync.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * DTO for the sync request body containing all sections to sync.
 */
public record SyncRequest(
    @NotEmpty @Valid List<SyncSeccion> secciones
) {
}
