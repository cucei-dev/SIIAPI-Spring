package dev.cucei.siiapi.modules.centro;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for {@link CentroUniversitario} entities.
 */
public interface CentroUniversitarioRepository extends JpaRepository<CentroUniversitario, Long> {

    Optional<CentroUniversitario> findBySiiauId(String siiauId);

    boolean existsBySiiauId(String siiauId);
}
