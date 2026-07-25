package dev.cucei.siiapi.modules.seccion;

import dev.cucei.siiapi.common.exceptions.ConflictException;
import dev.cucei.siiapi.common.exceptions.NotFoundException;
import dev.cucei.siiapi.modules.calendario.CalendarioRepository;
import dev.cucei.siiapi.modules.centro.CentroUniversitarioRepository;
import dev.cucei.siiapi.modules.materia.MateriaRepository;
import dev.cucei.siiapi.modules.profesor.Profesor;
import dev.cucei.siiapi.modules.profesor.ProfesorRepository;
import dev.cucei.siiapi.modules.seccion.dto.SeccionCreate;
import dev.cucei.siiapi.modules.seccion.dto.SeccionUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for seccion CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class SeccionService {

    private final SeccionRepository repository;
    private final CalendarioRepository calendarioRepository;
    private final CentroUniversitarioRepository centroRepository;
    private final MateriaRepository materiaRepository;
    private final ProfesorRepository profesorRepository;

    @Transactional
    public Seccion create(SeccionCreate data) {
        var calendario = calendarioRepository.findById(data.calendarioId())
                .orElseThrow(() -> new NotFoundException("Calendario not found."));
        var centro = centroRepository.findById(data.centroId())
                .orElseThrow(() -> new NotFoundException("CentroUniversitario not found."));
        var materia = materiaRepository.findById(data.materiaId())
                .orElseThrow(() -> new NotFoundException("Materia not found."));
        Profesor profesor = null;
        if (data.profesorId() != null) {
            profesor = profesorRepository.findById(data.profesorId())
                    .orElseThrow(() -> new NotFoundException("Profesor not found."));
        }

        if (repository.existsByNrcAndCalendarioId(data.nrc(), data.calendarioId())) {
            throw new ConflictException("Seccion with NRC '" + data.nrc() + "' already exists in that Calendario.");
        }

        Seccion entity = Seccion.builder()
                .name(data.name())
                .nrc(data.nrc())
                .cupos(data.cupos())
                .cuposDisponibles(data.cuposDisponibles())
                .est(data.est())
                .periodoInicio(data.periodoInicio())
                .periodoFin(data.periodoFin())
                .centro(centro)
                .materia(materia)
                .profesor(profesor)
                .calendario(calendario)
                .build();
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public Seccion getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Seccion not found."));
    }

    @Transactional(readOnly = true)
    public Optional<Seccion> findByNrcAndCalendario(String nrc, Long calendarioId) {
        return repository.findByNrcAndCalendarioId(nrc, calendarioId);
    }

    @Transactional(readOnly = true)
    public List<Seccion> list(Long nrc, Long centroId, Long materiaId, Long profesorId,
                              Long calendarioId, String search, int skip, int limit) {
        var page = repository.findAll(PageRequest.of(skip / limit, limit, Sort.by("id")));
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    @Transactional
    public Seccion update(Long id, SeccionUpdate data) {
        Seccion entity = getById(id);
        if (data.name() != null) entity.setName(data.name());
        if (data.nrc() != null) entity.setNrc(data.nrc());
        if (data.cupos() != null) entity.setCupos(data.cupos());
        if (data.cuposDisponibles() != null) entity.setCuposDisponibles(data.cuposDisponibles());
        if (data.est() != null) entity.setEst(data.est());
        if (data.periodoInicio() != null) entity.setPeriodoInicio(data.periodoInicio());
        if (data.periodoFin() != null) entity.setPeriodoFin(data.periodoFin());
        if (data.materiaId() != null) {
            var materia = materiaRepository.findById(data.materiaId())
                    .orElseThrow(() -> new NotFoundException("Materia not found."));
            entity.setMateria(materia);
        }
        if (data.profesorId() != null) {
            var profesor = profesorRepository.findById(data.profesorId())
                    .orElseThrow(() -> new NotFoundException("Profesor not found."));
            entity.setProfesor(profesor);
        }
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        Seccion entity = getById(id);
        repository.delete(entity);
    }
}
