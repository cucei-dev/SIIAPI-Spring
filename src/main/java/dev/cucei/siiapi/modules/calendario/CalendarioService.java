package dev.cucei.siiapi.modules.calendario;

import dev.cucei.siiapi.common.exceptions.ConflictException;
import dev.cucei.siiapi.common.exceptions.NotFoundException;
import dev.cucei.siiapi.modules.calendario.dto.CalendarioCreate;
import dev.cucei.siiapi.modules.calendario.dto.CalendarioUpdate;
import dev.cucei.siiapi.modules.seccion.SeccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for calendario CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class CalendarioService {

    private final CalendarioRepository repository;
    private final SeccionRepository seccionRepository;

    @Transactional
    public Calendario create(CalendarioCreate data) {
        if (repository.existsBySiiauId(data.siiauId())) {
            throw new ConflictException("Calendario with siiau_id '" + data.siiauId() + "' already exists.");
        }
        Calendario entity = Calendario.builder()
                .name(data.name())
                .siiauId(data.siiauId())
                .build();
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public Calendario getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Calendario not found."));
    }

    @Transactional(readOnly = true)
    public List<Calendario> list(String search, int skip, int limit) {
        int pageNumber = skip / limit;
        int offset = skip % limit;
        var page = repository.findAll(PageRequest.of(pageNumber, limit, Sort.by("id")));
        if (offset > 0 && page.hasContent()) {
            return page.getContent().subList(offset, page.getContent().size());
        }
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public long count(String search) {
        return repository.count();
    }

    @Transactional
    public Calendario update(Long id, CalendarioUpdate data) {
        Calendario entity = getById(id);
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
        Calendario entity = getById(id);
        repository.delete(entity);
    }
}
