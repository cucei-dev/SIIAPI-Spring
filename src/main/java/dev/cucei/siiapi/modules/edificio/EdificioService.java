package dev.cucei.siiapi.modules.edificio;

import dev.cucei.siiapi.common.exceptions.ConflictException;
import dev.cucei.siiapi.common.exceptions.NotFoundException;
import dev.cucei.siiapi.modules.centro.CentroUniversitarioRepository;
import dev.cucei.siiapi.modules.edificio.dto.EdificioCreate;
import dev.cucei.siiapi.modules.edificio.dto.EdificioUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for edificio CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class EdificioService {

    private final EdificioRepository repository;
    private final CentroUniversitarioRepository centroRepository;

    @Transactional
    public Edificio create(EdificioCreate data) {
        var centro = centroRepository.findById(data.centroId())
                .orElseThrow(() -> new NotFoundException("CentroUniversitario not found."));
        if (repository.existsByNameAndCentroId(data.name(), data.centroId())) {
            throw new ConflictException("Edificio '" + data.name() + "' already exists in that Centro.");
        }
        Edificio entity = Edificio.builder()
                .name(data.name())
                .centro(centro)
                .build();
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public Edificio getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Edificio not found."));
    }

    @Transactional(readOnly = true)
    public List<Edificio> list(Long centroId, String name, String search, int skip, int limit) {
        int pageNumber = skip / limit;
        int offset = skip % limit;
        var page = repository.findAll(PageRequest.of(pageNumber, limit, Sort.by("id")));
        if (offset > 0 && page.hasContent()) {
            return page.getContent().subList(offset, page.getContent().size());
        }
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    @Transactional
    public Edificio update(Long id, EdificioUpdate data) {
        Edificio entity = getById(id);
        if (data.name() != null) {
            entity.setName(data.name());
        }
        if (data.centroId() != null) {
            var centro = centroRepository.findById(data.centroId())
                    .orElseThrow(() -> new NotFoundException("CentroUniversitario not found."));
            entity.setCentro(centro);
        }
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        Edificio entity = getById(id);
        repository.delete(entity);
    }
}
