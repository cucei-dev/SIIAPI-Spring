package dev.cucei.siiapi.modules.seccion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link Seccion} entities.
 */
public interface SeccionRepository extends JpaRepository<Seccion, Long> {

    List<Seccion> findByCalendarioId(Long calendarioId);

    Optional<Seccion> findByNrcAndCalendarioId(String nrc, Long calendarioId);

    boolean existsByNrcAndCalendarioId(String nrc, Long calendarioId);

    List<Seccion> findByCentroIdAndCalendarioId(Long centroId, Long calendarioId);

    long countByCalendarioId(Long calendarioId);
}
