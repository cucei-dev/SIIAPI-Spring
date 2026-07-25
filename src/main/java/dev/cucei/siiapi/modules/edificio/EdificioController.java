package dev.cucei.siiapi.modules.edificio;

import dev.cucei.siiapi.common.pagination.PaginatedResponse;
import dev.cucei.siiapi.modules.centro.dto.CentroUniversitarioReadMinimal;
import dev.cucei.siiapi.modules.edificio.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for edificio CRUD operations.
 */
@RestController
@RequestMapping("/api/v2/edificios")
@RequiredArgsConstructor
public class EdificioController {

    private final EdificioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EdificioRead create(@Valid @RequestBody EdificioCreate data) {
        Edificio entity = service.create(data);
        return toRead(entity);
    }

    @GetMapping("/{id}")
    public EdificioRead getById(@PathVariable Long id) {
        Edificio entity = service.getById(id);
        return toRead(entity);
    }

    @GetMapping
    public PaginatedResponse<EdificioRead> list(
            @RequestParam(required = false) Long centroId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "100") int limit) {
        List<Edificio> entities = service.list(centroId, name, search, skip, limit);
        long total = service.count();
        List<EdificioRead> results = entities.stream().map(this::toRead).toList();
        return new PaginatedResponse<>(total, results);
    }

    @PutMapping("/{id}")
    public EdificioRead update(@PathVariable Long id, @Valid @RequestBody EdificioUpdate data) {
        Edificio entity = service.update(id, data);
        return toRead(entity);
    }

    @PatchMapping("/{id}")
    public EdificioRead patch(@PathVariable Long id, @Valid @RequestBody EdificioUpdate data) {
        Edificio entity = service.update(id, data);
        return toRead(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private EdificioRead toRead(Edificio entity) {
        return new EdificioRead(
                entity.getName(),
                entity.getCentro().getId(),
                entity.getId(),
                new CentroUniversitarioReadMinimal(
                        entity.getCentro().getName(),
                        entity.getCentro().getSiiauId(),
                        entity.getCentro().getId()
                ),
                List.of()
        );
    }
}
