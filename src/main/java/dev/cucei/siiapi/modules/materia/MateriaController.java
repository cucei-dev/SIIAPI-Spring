package dev.cucei.siiapi.modules.materia;

import dev.cucei.siiapi.common.pagination.PaginatedResponse;
import dev.cucei.siiapi.modules.materia.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for materia CRUD operations.
 */
@RestController
@RequestMapping("/api/v2/materias")
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MateriaRead create(@Valid @RequestBody MateriaCreate data) {
        Materia entity = service.create(data);
        return toRead(entity);
    }

    @GetMapping("/{id}")
    public MateriaRead getById(@PathVariable Long id) {
        Materia entity = service.getById(id);
        return toRead(entity);
    }

    @GetMapping
    public PaginatedResponse<MateriaRead> list(
            @RequestParam(required = false) String clave,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "100") int limit) {
        List<Materia> entities = service.list(clave, search, skip, limit);
        long total = service.count();
        List<MateriaRead> results = entities.stream().map(this::toRead).toList();
        return new PaginatedResponse<>(total, results);
    }

    @PutMapping("/{id}")
    public MateriaRead update(@PathVariable Long id, @Valid @RequestBody MateriaUpdate data) {
        Materia entity = service.update(id, data);
        return toRead(entity);
    }

    @PatchMapping("/{id}")
    public MateriaRead patch(@PathVariable Long id, @Valid @RequestBody MateriaUpdate data) {
        Materia entity = service.update(id, data);
        return toRead(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private MateriaRead toRead(Materia entity) {
        return new MateriaRead(
                entity.getName(),
                entity.getCreditos(),
                entity.getClave(),
                entity.getId(),
                List.of()
        );
    }
}
