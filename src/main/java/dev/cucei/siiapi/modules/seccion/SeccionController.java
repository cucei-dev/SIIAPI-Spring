package dev.cucei.siiapi.modules.seccion;

import dev.cucei.siiapi.common.pagination.PaginatedResponse;
import dev.cucei.siiapi.modules.calendario.dto.CalendarioReadMinimal;
import dev.cucei.siiapi.modules.centro.dto.CentroUniversitarioReadMinimal;
import dev.cucei.siiapi.modules.materia.dto.MateriaReadMinimal;
import dev.cucei.siiapi.modules.profesor.dto.ProfesorReadMinimal;
import dev.cucei.siiapi.modules.seccion.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for seccion CRUD operations.
 */
@RestController
@RequestMapping("/api/v2/secciones")
@RequiredArgsConstructor
public class SeccionController {

    private final SeccionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SeccionRead create(@Valid @RequestBody SeccionCreate data) {
        Seccion entity = service.create(data);
        return toRead(entity);
    }

    @GetMapping("/{id}")
    public SeccionRead getById(@PathVariable Long id) {
        Seccion entity = service.getById(id);
        return toRead(entity);
    }

    @GetMapping
    public PaginatedResponse<SeccionRead> list(
            @RequestParam(required = false) String nrc,
            @RequestParam(required = false) Long centroId,
            @RequestParam(required = false) Long materiaId,
            @RequestParam(required = false) Long profesorId,
            @RequestParam(required = false) Long calendarioId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "100") int limit) {
        List<Seccion> entities = service.list(
                null, centroId, materiaId, profesorId, calendarioId, search, skip, limit);
        long total = service.count();
        List<SeccionRead> results = entities.stream().map(this::toRead).toList();
        return new PaginatedResponse<>(total, results);
    }

    @PutMapping("/{id}")
    public SeccionRead update(@PathVariable Long id, @Valid @RequestBody SeccionUpdate data) {
        Seccion entity = service.update(id, data);
        return toRead(entity);
    }

    @PatchMapping("/{id}")
    public SeccionRead patch(@PathVariable Long id, @Valid @RequestBody SeccionUpdate data) {
        Seccion entity = service.update(id, data);
        return toRead(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private SeccionRead toRead(Seccion entity) {
        return new SeccionRead(
                entity.getName(),
                entity.getNrc(),
                entity.getCupos(),
                entity.getCuposDisponibles(),
                entity.getEst(),
                entity.getPeriodoInicio(),
                entity.getPeriodoFin(),
                entity.getCentro().getId(),
                entity.getMateria().getId(),
                entity.getProfesor() != null ? entity.getProfesor().getId() : null,
                entity.getCalendario().getId(),
                entity.getId(),
                new CentroUniversitarioReadMinimal(
                        entity.getCentro().getName(),
                        entity.getCentro().getSiiauId(),
                        entity.getCentro().getId()
                ),
                new MateriaReadMinimal(
                        entity.getMateria().getName(),
                        entity.getMateria().getCreditos(),
                        entity.getMateria().getClave(),
                        entity.getMateria().getId()
                ),
                entity.getProfesor() != null ? new ProfesorReadMinimal(
                        entity.getProfesor().getName(),
                        entity.getProfesor().getId()
                ) : null,
                new CalendarioReadMinimal(
                        entity.getCalendario().getName(),
                        entity.getCalendario().getSiiauId(),
                        entity.getCalendario().getId()
                ),
                List.of()
        );
    }

    public static SeccionReadMinimal toReadMinimal(Seccion entity) {
        return new SeccionReadMinimal(
                entity.getName(),
                entity.getNrc(),
                entity.getCupos(),
                entity.getCuposDisponibles(),
                entity.getEst(),
                entity.getPeriodoInicio(),
                entity.getPeriodoFin(),
                entity.getCentro().getId(),
                entity.getMateria().getId(),
                entity.getProfesor() != null ? entity.getProfesor().getId() : null,
                entity.getCalendario().getId(),
                entity.getId()
        );
    }
}
