package dev.cucei.siiapi.modules.clase;

import dev.cucei.siiapi.common.exceptions.ConflictException;
import dev.cucei.siiapi.common.exceptions.NotFoundException;
import dev.cucei.siiapi.modules.aula.AulaRepository;
import dev.cucei.siiapi.modules.clase.dto.ClaseCreate;
import dev.cucei.siiapi.modules.clase.dto.ClaseUpdate;
import dev.cucei.siiapi.modules.seccion.SeccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for clase CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class ClaseService {

    private final ClaseRepository repository;
    private final SeccionRepository seccionRepository;
    private final AulaRepository aulaRepository;

    @Transactional
    public Clase create(ClaseCreate data) {
        var seccion = seccionRepository.findById(data.seccionId())
                .orElseThrow(() -> new NotFoundException("Seccion not found."));
        var aula = data.aulaId() != null
                ? aulaRepository.findById(data.aulaId())
                    .orElseThrow(() -> new NotFoundException("Aula not found."))
                : null;

        Clase entity = Clase.builder()
                .sesion(data.sesion())
                .horaInicio(data.horaInicio())
                .horaFin(data.horaFin())
                .dia(data.dia())
                .seccion(seccion)
                .aula(aula)
                .build();
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public Clase getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Clase not found."));
    }

    @Transactional(readOnly = true)
    public List<Clase> list(Long seccionId, Long aulaId, int skip, int limit) {
        var page = repository.findAll(PageRequest.of(skip / limit, limit, Sort.by("id")));
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public List<Clase> listBySeccionId(Long seccionId) {
        return repository.findBySeccionId(seccionId);
    }

    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    @Transactional
    public Clase update(Long id, ClaseUpdate data) {
        Clase entity = getById(id);
        if (data.sesion() != null) entity.setSesion(data.sesion());
        if (data.horaInicio() != null) entity.setHoraInicio(data.horaInicio());
        if (data.horaFin() != null) entity.setHoraFin(data.horaFin());
        if (data.dia() != null) entity.setDia(data.dia());
        if (data.aulaId() != null) {
            var aula = aulaRepository.findById(data.aulaId())
                    .orElseThrow(() -> new NotFoundException("Aula not found."));
            entity.setAula(aula);
        }
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        Clase entity = getById(id);
        repository.delete(entity);
    }

    @Transactional
    public void deleteBySeccionId(Long seccionId) {
        repository.deleteBySeccionId(seccionId);
    }
}
