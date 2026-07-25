package dev.cucei.siiapi.modules.edificio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link Edificio} entities.
 */
public interface EdificioRepository extends JpaRepository<Edificio, Long> {

    List<Edificio> findByCentroId(Long centroId);

    Optional<Edificio> findByNameAndCentroId(String name, Long centroId);

    boolean existsByNameAndCentroId(String name, Long centroId);
}
