package dev.cucei.siiapi.modules.centro;

import dev.cucei.siiapi.common.pagination.PaginatedResponse;
import dev.cucei.siiapi.modules.centro.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for centro universitario CRUD operations.
 */
@RestController
@RequestMapping("/api/v2/centros")
@RequiredArgsConstructor
public class CentroUniversitarioController {

    private final CentroUniversitarioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CentroUniversitarioRead create(@Valid @RequestBody CentroUniversitarioCreate data) {
        CentroUniversitario entity = service.create(data);
        return toRead(entity);
    }

    @GetMapping("/{id}")
    public CentroUniversitarioRead getById(@PathVariable Long id) {
        CentroUniversitario entity = service.getById(id);
        return toRead(entity);
    }

    @GetMapping
    public PaginatedResponse<CentroUniversitarioRead> list(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "100") int limit) {
        List<CentroUniversitario> entities = service.list(search, skip, limit);
        long total = service.count();
        List<CentroUniversitarioRead> results = entities.stream()
                .map(this::toRead)
                .toList();
        return new PaginatedResponse<>(total, results);
    }

    @PutMapping("/{id}")
    public CentroUniversitarioRead update(@PathVariable Long id, @Valid @RequestBody CentroUniversitarioUpdate data) {
        CentroUniversitario entity = service.update(id, data);
        return toRead(entity);
    }

    @PatchMapping("/{id}")
    public CentroUniversitarioRead patch(@PathVariable Long id, @Valid @RequestBody CentroUniversitarioUpdate data) {
        CentroUniversitario entity = service.update(id, data);
        return toRead(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private CentroUniversitarioRead toRead(CentroUniversitario entity) {
        return new CentroUniversitarioRead(
                entity.getName(),
                entity.getSiiauId(),
                entity.getId(),
                List.of(),
                List.of()
        );
    }
}
