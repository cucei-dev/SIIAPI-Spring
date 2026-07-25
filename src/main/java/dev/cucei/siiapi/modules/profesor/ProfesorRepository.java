package dev.cucei.siiapi.modules.profesor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for {@link Profesor} entities.
 */
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    Optional<Profesor> findByName(String name);

    boolean existsByName(String name);
}
