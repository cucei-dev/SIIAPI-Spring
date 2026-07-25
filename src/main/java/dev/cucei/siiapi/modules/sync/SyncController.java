package dev.cucei.siiapi.modules.sync;

import dev.cucei.siiapi.modules.sync.dto.SyncRequest;
import dev.cucei.siiapi.modules.sync.dto.SyncResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for synchronizing SIIAU data with the database.
 * <p>
 * This endpoint receives the complete state of sections from SIIAU
 * and performs an intelligent diff to create, update, or delete only what changed.
 */
@RestController
@RequestMapping("/api/v2/sync")
@RequiredArgsConstructor
public class SyncController {

    private final SyncService service;

    /**
     * Synchronizes all sections for a given calendar and university center.
     * <p>
     * The request body should contain ALL sections currently in SIIAU.
     * The service will:
     * <ul>
     *   <li>Create sections that don't exist in the database</li>
     *   <li>Update sections that changed</li>
     *   <li>Remove sections no longer in SIIAU</li>
     *   <li>For each section, sync classes using natural keys</li>
     * </ul>
     *
     * @param calendarioId the calendar ID
     * @param centroId the university center ID
     * @param request all sections from SIIAU
     * @return sync statistics
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public SyncResponse sync(
            @RequestParam Long calendarioId,
            @RequestParam Long centroId,
            @Valid @RequestBody SyncRequest request) {
        return service.sync(calendarioId, centroId, request);
    }
}
