package dev.cucei.siiapi.modules.centro;

import dev.cucei.siiapi.common.exceptions.ConflictException;
import dev.cucei.siiapi.common.exceptions.NotFoundException;
import dev.cucei.siiapi.modules.centro.dto.CentroUniversitarioCreate;
import dev.cucei.siiapi.modules.centro.dto.CentroUniversitarioUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for centro universitario CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class CentroUniversitarioService {

    private final CentroUniversitarioRepository repository;

    @Transactional
    public CentroUniversitario create(CentroUniversitarioCreate data) {
        if (repository.existsBySiiauId(data.siiauId())) {
            throw new ConflictException("CentroUniversitario with siiau_id '" + data.siiauId() + "' already exists.");
        }
        CentroUniversitario entity = CentroUniversitario.builder()
                .name(data.name())
                .siiauId(data.siiauId())
                .build();
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public CentroUniversitario getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("CentroUniversitario not found."));
    }

    @Transactional(readOnly = true)
    public List<CentroUniversitario> list(String search, int skip, int limit) {
        var page = repository.findAll(PageRequest.of(skip / limit, limit, Sort.by("id")));
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    @Transactional
    public CentroUniversitario update(Long id, CentroUniversitarioUpdate data) {
        CentroUniversitario entity = getById(id);
        if (data.name() != null) {
            entity.setName(data.name());
        }
        if (data.siiauId() != null) {
            entity.setSiiauId(data.siiauId());
        }
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        CentroUniversitario entity = getById(id);
        repository.delete(entity);
    }
}
