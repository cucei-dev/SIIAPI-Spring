package dev.cucei.siiapi.modules.calendario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository for {@link Calendario} entities.
 */
public interface CalendarioRepository extends JpaRepository<Calendario, Long> {

    Optional<Calendario> findBySiiauId(String siiauId);

    boolean existsBySiiauId(String siiauId);
}
