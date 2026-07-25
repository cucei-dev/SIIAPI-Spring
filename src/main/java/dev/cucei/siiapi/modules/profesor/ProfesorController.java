package dev.cucei.siiapi.modules.profesor;

import dev.cucei.siiapi.common.pagination.PaginatedResponse;
import dev.cucei.siiapi.modules.profesor.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for profesor CRUD operations.
 */
@RestController
@RequestMapping("/api/v2/profesores")
@RequiredArgsConstructor
public class ProfesorController {

    private final ProfesorService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfesorRead create(@Valid @RequestBody ProfesorCreate data) {
        Profesor entity = service.create(data);
        return toRead(entity);
    }

    @GetMapping("/{id}")
    public ProfesorRead getById(@PathVariable Long id) {
        Profesor entity = service.getById(id);
        return toRead(entity);
    }

    @GetMapping
    public PaginatedResponse<ProfesorRead> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "100") int limit) {
        List<Profesor> entities = service.list(name, search, skip, limit);
        long total = service.count();
        List<ProfesorRead> results = entities.stream().map(this::toRead).toList();
        return new PaginatedResponse<>(total, results);
    }

    @PutMapping("/{id}")
    public ProfesorRead update(@PathVariable Long id, @Valid @RequestBody ProfesorUpdate data) {
        Profesor entity = service.update(id, data);
        return toRead(entity);
    }

    @PatchMapping("/{id}")
    public ProfesorRead patch(@PathVariable Long id, @Valid @RequestBody ProfesorUpdate data) {
        Profesor entity = service.update(id, data);
        return toRead(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private ProfesorRead toRead(Profesor entity) {
        return new ProfesorRead(
                entity.getName(),
                entity.getId(),
                List.of()
        );
    }
}
