package dev.cucei.siiapi.modules.calendario;

import dev.cucei.siiapi.common.pagination.PaginatedResponse;
import dev.cucei.siiapi.modules.calendario.dto.*;
import dev.cucei.siiapi.modules.seccion.SeccionRepository;
import dev.cucei.siiapi.modules.seccion.SeccionController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for calendario CRUD operations.
 */
@RestController
@RequestMapping("/api/v1/calendarios")
@RequiredArgsConstructor
public class CalendarioController {

    private final CalendarioService service;
    private final SeccionRepository seccionRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CalendarioRead create(@Valid @RequestBody CalendarioCreate data) {
        Calendario entity = service.create(data);
        return toRead(entity, List.of());
    }

    @GetMapping("/{id}")
    public CalendarioRead getById(@PathVariable Long id) {
        Calendario entity = service.getById(id);
        var secciones = seccionRepository.findByCalendarioId(id);
        return toRead(entity, secciones.stream().map(SeccionController::toReadMinimal).toList());
    }

    @GetMapping
    public PaginatedResponse<CalendarioRead> list(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "100") int limit) {
        List<Calendario> entities = service.list(search, skip, limit);
        long total = service.count(search);
        List<CalendarioRead> results = entities.stream()
                .map(e -> toRead(e, List.of()))
                .toList();
        return new PaginatedResponse<>(total, results);
    }

    @PutMapping("/{id}")
    public CalendarioRead update(@PathVariable Long id, @Valid @RequestBody CalendarioUpdate data) {
        Calendario entity = service.update(id, data);
        return toRead(entity, List.of());
    }

    @PatchMapping("/{id}")
    public CalendarioRead patch(@PathVariable Long id, @Valid @RequestBody CalendarioUpdate data) {
        Calendario entity = service.update(id, data);
        return toRead(entity, List.of());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private CalendarioRead toRead(Calendario entity, List<?> secciones) {
        return new CalendarioRead(
                entity.getName(),
                entity.getSiiauId(),
                entity.getId(),
                List.of()
        );
    }
}
