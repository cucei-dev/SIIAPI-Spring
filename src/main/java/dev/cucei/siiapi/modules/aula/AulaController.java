package dev.cucei.siiapi.modules.aula;

import dev.cucei.siiapi.common.pagination.PaginatedResponse;
import dev.cucei.siiapi.modules.aula.dto.*;
import dev.cucei.siiapi.modules.edificio.dto.EdificioReadMinimal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for aula CRUD operations.
 */
@RestController
@RequestMapping("/api/v2/aulas")
@RequiredArgsConstructor
public class AulaController {

    private final AulaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AulaRead create(@Valid @RequestBody AulaCreate data) {
        Aula entity = service.create(data);
        return toRead(entity);
    }

    @GetMapping("/{id}")
    public AulaRead getById(@PathVariable Long id) {
        Aula entity = service.getById(id);
        return toRead(entity);
    }

    @GetMapping
    public PaginatedResponse<AulaRead> list(
            @RequestParam(required = false) Long edificioId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "100") int limit) {
        List<Aula> entities = service.list(edificioId, name, search, skip, limit);
        long total = service.count();
        List<AulaRead> results = entities.stream().map(this::toRead).toList();
        return new PaginatedResponse<>(total, results);
    }

    @PutMapping("/{id}")
    public AulaRead update(@PathVariable Long id, @Valid @RequestBody AulaUpdate data) {
        Aula entity = service.update(id, data);
        return toRead(entity);
    }

    @PatchMapping("/{id}")
    public AulaRead patch(@PathVariable Long id, @Valid @RequestBody AulaUpdate data) {
        Aula entity = service.update(id, data);
        return toRead(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private AulaRead toRead(Aula entity) {
        return new AulaRead(
                entity.getName(),
                entity.getEdificio().getId(),
                entity.getId(),
                new EdificioReadMinimal(
                        entity.getEdificio().getName(),
                        entity.getEdificio().getCentro().getId(),
                        entity.getEdificio().getId()
                ),
                List.of()
        );
    }
}
