package dev.cucei.siiapi.modules.materia;

import dev.cucei.siiapi.common.exceptions.ConflictException;
import dev.cucei.siiapi.common.exceptions.NotFoundException;
import dev.cucei.siiapi.modules.materia.dto.MateriaCreate;
import dev.cucei.siiapi.modules.materia.dto.MateriaUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for materia CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository repository;

    @Transactional
    public Materia create(MateriaCreate data) {
        if (repository.existsByClave(data.clave())) {
            throw new ConflictException("Materia with clave '" + data.clave() + "' already exists.");
        }
        Materia entity = Materia.builder()
                .name(data.name())
                .creditos(data.creditos())
                .clave(data.clave())
                .build();
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public Materia getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Materia not found."));
    }

    @Transactional(readOnly = true)
    public List<Materia> list(String clave, String search, int skip, int limit) {
        var page = repository.findAll(PageRequest.of(skip / limit, limit, Sort.by("id")));
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    @Transactional
    public Materia update(Long id, MateriaUpdate data) {
        Materia entity = getById(id);
        if (data.name() != null) {
            entity.setName(data.name());
        }
        if (data.creditos() != null) {
            entity.setCreditos(data.creditos());
        }
        if (data.clave() != null) {
            entity.setClave(data.clave());
        }
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        Materia entity = getById(id);
        repository.delete(entity);
    }
}
