package dev.cucei.siiapi.modules.aula;

import dev.cucei.siiapi.common.exceptions.ConflictException;
import dev.cucei.siiapi.common.exceptions.NotFoundException;
import dev.cucei.siiapi.modules.aula.dto.AulaCreate;
import dev.cucei.siiapi.modules.aula.dto.AulaUpdate;
import dev.cucei.siiapi.modules.edificio.EdificioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for aula CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class AulaService {

    private final AulaRepository repository;
    private final EdificioRepository edificioRepository;

    @Transactional
    public Aula create(AulaCreate data) {
        var edificio = edificioRepository.findById(data.edificioId())
                .orElseThrow(() -> new NotFoundException("Edificio not found."));
        if (repository.existsByNameAndEdificioId(data.name(), data.edificioId())) {
            throw new ConflictException("Aula '" + data.name() + "' already exists in that Edificio.");
        }
        Aula entity = Aula.builder()
                .name(data.name())
                .edificio(edificio)
                .build();
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public Aula getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aula not found."));
    }

    @Transactional(readOnly = true)
    public List<Aula> list(Long edificioId, String name, String search, int skip, int limit) {
        var page = repository.findAll(PageRequest.of(skip / limit, limit, Sort.by("id")));
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    @Transactional
    public Aula update(Long id, AulaUpdate data) {
        Aula entity = getById(id);
        if (data.name() != null) {
            entity.setName(data.name());
        }
        if (data.edificioId() != null) {
            var edificio = edificioRepository.findById(data.edificioId())
                    .orElseThrow(() -> new NotFoundException("Edificio not found."));
            entity.setEdificio(edificio);
        }
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        Aula entity = getById(id);
        repository.delete(entity);
    }
}
