package dev.cucei.siiapi.modules.profesor;

import dev.cucei.siiapi.common.exceptions.ConflictException;
import dev.cucei.siiapi.common.exceptions.NotFoundException;
import dev.cucei.siiapi.modules.profesor.dto.ProfesorCreate;
import dev.cucei.siiapi.modules.profesor.dto.ProfesorUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for profesor CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class ProfesorService {

    private final ProfesorRepository repository;

    @Transactional
    public Profesor create(ProfesorCreate data) {
        if (repository.existsByName(data.name())) {
            throw new ConflictException("Profesor with name '" + data.name() + "' already exists.");
        }
        Profesor entity = Profesor.builder()
                .name(data.name())
                .build();
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public Profesor getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profesor not found."));
    }

    @Transactional(readOnly = true)
    public List<Profesor> list(String name, String search, int skip, int limit) {
        var page = repository.findAll(PageRequest.of(skip / limit, limit, Sort.by("id")));
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    @Transactional
    public Profesor update(Long id, ProfesorUpdate data) {
        Profesor entity = getById(id);
        if (data.name() != null) {
            entity.setName(data.name());
        }
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        Profesor entity = getById(id);
        repository.delete(entity);
    }
}
