package dev.cucei.siiapi.modules.clase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for {@link Clase} entities.
 */
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    List<Clase> findBySeccionId(Long seccionId);

    void deleteBySeccionId(Long seccionId);

    long countBySeccionId(Long seccionId);
}
