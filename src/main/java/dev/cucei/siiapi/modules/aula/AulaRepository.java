package dev.cucei.siiapi.modules.aula;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link Aula} entities.
 */
public interface AulaRepository extends JpaRepository<Aula, Long> {

    List<Aula> findByEdificioId(Long edificioId);

    Optional<Aula> findByNameAndEdificioId(String name, Long edificioId);

    boolean existsByNameAndEdificioId(String name, Long edificioId);
}
