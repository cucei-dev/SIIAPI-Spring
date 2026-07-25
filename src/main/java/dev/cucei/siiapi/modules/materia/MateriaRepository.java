package dev.cucei.siiapi.modules.materia;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for {@link Materia} entities.
 */
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    Optional<Materia> findByClave(String clave);

    boolean existsByClave(String clave);
}
