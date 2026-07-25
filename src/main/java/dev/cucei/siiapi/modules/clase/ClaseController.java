package dev.cucei.siiapi.modules.clase;

import dev.cucei.siiapi.common.pagination.PaginatedResponse;
import dev.cucei.siiapi.modules.aula.dto.AulaReadMinimal;
import dev.cucei.siiapi.modules.clase.dto.*;
import dev.cucei.siiapi.modules.seccion.dto.SeccionReadMinimal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for clase CRUD operations.
 */
@RestController
@RequestMapping("/api/v1/clases")
@RequiredArgsConstructor
public class ClaseController {

    private final ClaseService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClaseRead create(@Valid @RequestBody ClaseCreate data) {
        Clase entity = service.create(data);
        return toRead(entity);
    }

    @GetMapping("/{id}")
    public ClaseRead getById(@PathVariable Long id) {
        Clase entity = service.getById(id);
        return toRead(entity);
    }

    @GetMapping
    public PaginatedResponse<ClaseRead> list(
            @RequestParam(required = false) Long seccionId,
            @RequestParam(required = false) Long aulaId,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "100") int limit) {
        List<Clase> entities = service.list(seccionId, aulaId, skip, limit);
        long total = service.count();
        List<ClaseRead> results = entities.stream().map(this::toRead).toList();
        return new PaginatedResponse<>(total, results);
    }

    @PutMapping("/{id}")
    public ClaseRead update(@PathVariable Long id, @Valid @RequestBody ClaseUpdate data) {
        Clase entity = service.update(id, data);
        return toRead(entity);
    }

    @PatchMapping("/{id}")
    public ClaseRead patch(@PathVariable Long id, @Valid @RequestBody ClaseUpdate data) {
        Clase entity = service.update(id, data);
        return toRead(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private ClaseRead toRead(Clase entity) {
        return new ClaseRead(
                entity.getSesion(),
                entity.getHoraInicio(),
                entity.getHoraFin(),
                entity.getDia(),
                entity.getSeccion().getId(),
                entity.getAula() != null ? entity.getAula().getId() : null,
                entity.getId(),
                null,
                entity.getAula() != null ? new AulaReadMinimal(
                        entity.getAula().getName(),
                        entity.getAula().getEdificio().getId(),
                        entity.getAula().getId()
                ) : null
        );
    }
}
