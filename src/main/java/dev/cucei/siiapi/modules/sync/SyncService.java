package dev.cucei.siiapi.modules.sync;

import dev.cucei.siiapi.common.exceptions.NotFoundException;
import dev.cucei.siiapi.modules.aula.Aula;
import dev.cucei.siiapi.modules.aula.AulaRepository;
import dev.cucei.siiapi.modules.calendario.Calendario;
import dev.cucei.siiapi.modules.calendario.CalendarioRepository;
import dev.cucei.siiapi.modules.centro.CentroUniversitario;
import dev.cucei.siiapi.modules.centro.CentroUniversitarioRepository;
import dev.cucei.siiapi.modules.clase.Clase;
import dev.cucei.siiapi.modules.clase.ClaseRepository;
import dev.cucei.siiapi.modules.edificio.Edificio;
import dev.cucei.siiapi.modules.edificio.EdificioRepository;
import dev.cucei.siiapi.modules.materia.Materia;
import dev.cucei.siiapi.modules.materia.MateriaRepository;
import dev.cucei.siiapi.modules.profesor.Profesor;
import dev.cucei.siiapi.modules.profesor.ProfesorRepository;
import dev.cucei.siiapi.modules.seccion.Seccion;
import dev.cucei.siiapi.modules.seccion.SeccionRepository;
import dev.cucei.siiapi.modules.sync.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service that synchronizes SIIAU data with the database using a diff algorithm.
 * <p>
 * For each section, it:
 * <ol>
 *   <li>Creates new sections that don't exist yet</li>
 *   <li>Updates existing sections only if something changed</li>
 *   <li>For classes within each section, uses a natural key (sesion, horaInicio, horaFin, dia)
 *       to determine create/update/delete</li>
 *   <li>Preserves IDs when nothing changed</li>
 * </ol>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SyncService {

    private final CalendarioRepository calendarioRepository;
    private final CentroUniversitarioRepository centroRepository;
    private final MateriaRepository materiaRepository;
    private final ProfesorRepository profesorRepository;
    private final EdificioRepository edificioRepository;
    private final AulaRepository aulaRepository;
    private final SeccionRepository seccionRepository;
    private final ClaseRepository claseRepository;

    private static final DateTimeFormatter PERIOD_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yy");

    /**
     * Synchronizes all sections from the request with the database.
     *
     * @param calendarioId the calendar ID
     * @param centroId the university center ID
     * @param request the sync request containing all sections
     * @return statistics about the sync operation
     */
    @Transactional
    public SyncResponse sync(Long calendarioId, Long centroId, SyncRequest request) {
        Calendario calendario = calendarioRepository.findById(calendarioId)
                .orElseThrow(() -> new NotFoundException("Calendario not found."));
        CentroUniversitario centro = centroRepository.findById(centroId)
                .orElseThrow(() -> new NotFoundException("CentroUniversitario not found."));

        SyncStats stats = new SyncStats();

        Set<String> incomingNrcs = request.secciones().stream()
                .map(SyncSeccion::nrc)
                .collect(Collectors.toSet());

        List<Seccion> existingSections = seccionRepository.findByCentroIdAndCalendarioId(centroId, calendarioId);
        for (Seccion existing : existingSections) {
            if (!incomingNrcs.contains(existing.getNrc())) {
                claseRepository.deleteBySeccionId(existing.getId());
                seccionRepository.delete(existing);
                stats.seccionesEliminadas++;
            }
        }

        Map<String, Seccion> existingByNrc = existingSections.stream()
                .filter(s -> incomingNrcs.contains(s.getNrc()))
                .collect(Collectors.toMap(Seccion::getNrc, s -> s));

        for (SyncSeccion syncSeccion : request.secciones()) {
            Seccion existingSeccion = existingByNrc.get(syncSeccion.nrc());

            if (existingSeccion != null) {
                updateSeccionIfNeeded(existingSeccion, syncSeccion, centro, calendario, stats);
                syncClasesForSeccion(existingSeccion, syncSeccion, centro, stats);
            } else {
                Seccion newSeccion = createSeccion(syncSeccion, centro, calendario, stats);
                syncClasesForSeccion(newSeccion, syncSeccion, centro, stats);
            }
        }

        log.info("Sync completed for calendario={}, centro={}: created={}, updated={}, deleted={}",
                calendarioId, centroId,
                stats.seccionesCreadas, stats.seccionesActualizadas, stats.seccionesEliminadas);

        return new SyncResponse(
                stats.seccionesCreadas,
                stats.seccionesActualizadas,
                stats.seccionesEliminadas,
                stats.clasesCreadas,
                stats.clasesActualizadas,
                stats.clasesEliminadas,
                stats.materiasCreadas,
                stats.profesoresCreados,
                stats.edificiosCreados,
                stats.aulasCreadas
        );
    }

    private void updateSeccionIfNeeded(Seccion existing, SyncSeccion sync,
                                       CentroUniversitario centro, Calendario calendario,
                                       SyncStats stats) {
        Materia materia = getOrCreateMateria(sync.clave(), sync.materia(), sync.cr(), stats);
        Profesor profesor = sync.profesor() != null && !sync.profesor().isBlank()
                ? getOrCreateProfesor(sync.profesor(), stats) : null;

        boolean changed = false;

        if (!Objects.equals(existing.getCupos(), sync.cup())) {
            existing.setCupos(sync.cup());
            changed = true;
        }
        if (!Objects.equals(existing.getCuposDisponibles(), sync.dis())) {
            existing.setCuposDisponibles(sync.dis());
            changed = true;
        }
        if (!Objects.equals(existing.getEst(), sync.est())) {
            existing.setEst(sync.est());
            changed = true;
        }
        if (!Objects.equals(existing.getMateria().getId(), materia.getId())) {
            existing.setMateria(materia);
            changed = true;
        }
        if (!Objects.equals(
                existing.getProfesor() != null ? existing.getProfesor().getId() : null,
                profesor != null ? profesor.getId() : null)) {
            existing.setProfesor(profesor);
            changed = true;
        }

        if (sync.periodo() != null && !sync.periodo().isBlank()) {
            LocalDateTime[] periodo = parsePeriodo(sync.periodo());
            if (periodo != null) {
                if (!Objects.equals(existing.getPeriodoInicio(), periodo[0])) {
                    existing.setPeriodoInicio(periodo[0]);
                    changed = true;
                }
                if (!Objects.equals(existing.getPeriodoFin(), periodo[1])) {
                    existing.setPeriodoFin(periodo[1]);
                    changed = true;
                }
            }
        }

        if (changed) {
            seccionRepository.save(existing);
            stats.seccionesActualizadas++;
        }
    }

    private Seccion createSeccion(SyncSeccion sync, CentroUniversitario centro,
                                  Calendario calendario, SyncStats stats) {
        Materia materia = getOrCreateMateria(sync.clave(), sync.materia(), sync.cr(), stats);
        Profesor profesor = sync.profesor() != null && !sync.profesor().isBlank()
                ? getOrCreateProfesor(sync.profesor(), stats) : null;

        LocalDateTime[] periodo = sync.periodo() != null ? parsePeriodo(sync.periodo()) : null;

        Seccion seccion = Seccion.builder()
                .name(sync.sec())
                .nrc(sync.nrc())
                .cupos(sync.cup())
                .cuposDisponibles(sync.dis())
                .est(sync.est())
                .periodoInicio(periodo != null ? periodo[0] : null)
                .periodoFin(periodo != null ? periodo[1] : null)
                .centro(centro)
                .materia(materia)
                .profesor(profesor)
                .calendario(calendario)
                .build();

        seccion = seccionRepository.save(seccion);
        stats.seccionesCreadas++;
        return seccion;
    }

    private void syncClasesForSeccion(Seccion seccion, SyncSeccion sync,
                                      CentroUniversitario centro, SyncStats stats) {
        if (sync.clases() == null || sync.clases().isEmpty()) {
            return;
        }

        List<Clase> existingClases = claseRepository.findBySeccionId(seccion.getId());

        Map<String, Clase> existingByKey = existingClases.stream()
                .collect(Collectors.toMap(this::claseKey, c -> c, (a, b) -> a));

        Set<String> incomingKeys = sync.clases().stream()
                .map(this::syncClaseKey)
                .collect(Collectors.toSet());

        for (Clase existing : existingClases) {
            if (!incomingKeys.contains(claseKey(existing))) {
                claseRepository.delete(existing);
                stats.clasesEliminadas++;
            }
        }

        existingByKey.entrySet().removeIf(e -> !incomingKeys.contains(e.getKey()));

        for (SyncClase syncClase : sync.clases()) {
            String key = syncClaseKey(syncClase);
            LocalTime horaInicio = parseTime(syncClase.horaInicio());
            LocalTime horaFin = parseTime(syncClase.horaFin());

            Aula aula = resolveAula(syncClase, centro, stats);

            Clase existingClase = existingByKey.get(key);

            if (existingClase != null) {
                boolean changed = false;
                if (!Objects.equals(existingClase.getAula() != null ? existingClase.getAula().getId() : null,
                        aula != null ? aula.getId() : null)) {
                    existingClase.setAula(aula);
                    changed = true;
                }
                if (changed) {
                    claseRepository.save(existingClase);
                    stats.clasesActualizadas++;
                }
            } else {
                Clase clase = Clase.builder()
                        .sesion(syncClase.sesion())
                        .horaInicio(horaInicio)
                        .horaFin(horaFin)
                        .dia(syncClase.dia())
                        .seccion(seccion)
                        .aula(aula)
                        .build();
                claseRepository.save(clase);
                stats.clasesCreadas++;
            }
        }
    }

    private Aula resolveAula(SyncClase syncClase, CentroUniversitario centro, SyncStats stats) {
        if (syncClase.edificio() == null || syncClase.edificio().isBlank()) {
            return null;
        }

        Edificio edificio = edificioRepository.findByNameAndCentroId(syncClase.edificio(), centro.getId())
                .orElseGet(() -> {
                    Edificio newEdificio = Edificio.builder()
                            .name(syncClase.edificio())
                            .centro(centro)
                            .build();
                    stats.edificiosCreados++;
                    return edificioRepository.save(newEdificio);
                });

        if (syncClase.aula() == null || syncClase.aula().isBlank()) {
            return null;
        }

        return aulaRepository.findByNameAndEdificioId(syncClase.aula(), edificio.getId())
                .orElseGet(() -> {
                    Aula newAula = Aula.builder()
                            .name(syncClase.aula())
                            .edificio(edificio)
                            .build();
                    stats.aulasCreadas++;
                    return aulaRepository.save(newAula);
                });
    }

    private Materia getOrCreateMateria(String clave, String nombre, int creditos, SyncStats stats) {
        return materiaRepository.findByClave(clave)
                .orElseGet(() -> {
                    Materia materia = Materia.builder()
                            .clave(clave)
                            .name(nombre)
                            .creditos(creditos)
                            .build();
                    stats.materiasCreadas++;
                    return materiaRepository.save(materia);
                });
    }

    private Profesor getOrCreateProfesor(String nombre, SyncStats stats) {
        return profesorRepository.findByName(nombre)
                .orElseGet(() -> {
                    Profesor profesor = Profesor.builder()
                            .name(nombre)
                            .build();
                    stats.profesoresCreados++;
                    return profesorRepository.save(profesor);
                });
    }

    private String claseKey(Clase clase) {
        return (clase.getSesion() != null ? clase.getSesion() : "") + "|"
                + (clase.getHoraInicio() != null ? clase.getHoraInicio().toString() : "") + "|"
                + (clase.getHoraFin() != null ? clase.getHoraFin().toString() : "") + "|"
                + (clase.getDia() != null ? clase.getDia().toString() : "");
    }

    private String syncClaseKey(SyncClase sync) {
        return (sync.sesion() != null ? sync.sesion() : "") + "|"
                + (sync.horaInicio() != null ? sync.horaInicio() : "") + "|"
                + (sync.horaFin() != null ? sync.horaFin() : "") + "|"
                + (sync.dia() != null ? sync.dia().toString() : "");
    }

    private LocalTime parseTime(String timeStr) {
        if (timeStr == null || timeStr.isBlank()) return null;
        try {
            String cleaned = timeStr.trim();
            if (cleaned.length() == 4) {
                return LocalTime.of(
                        Integer.parseInt(cleaned.substring(0, 2)),
                        Integer.parseInt(cleaned.substring(2, 4))
                );
            }
            return LocalTime.parse(cleaned, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception e) {
            log.warn("Failed to parse time: {}", timeStr);
            return null;
        }
    }

    private LocalDateTime[] parsePeriodo(String periodo) {
        if (periodo == null || periodo.isBlank()) return null;
        try {
            String[] parts = periodo.split("\\s*-\\s*");
            if (parts.length == 2) {
                LocalDateTime inicio = LocalDateTime.parse(parts[0].trim() + " 00:00",
                        DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
                LocalDateTime fin = LocalDateTime.parse(parts[1].trim() + " 23:59",
                        DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
                return new LocalDateTime[]{inicio, fin};
            }
        } catch (Exception e) {
            log.warn("Failed to parse periodo: {}", periodo);
        }
        return null;
    }

    private static class SyncStats {
        long seccionesCreadas = 0;
        long seccionesActualizadas = 0;
        long seccionesEliminadas = 0;
        long clasesCreadas = 0;
        long clasesActualizadas = 0;
        long clasesEliminadas = 0;
        long materiasCreadas = 0;
        long profesoresCreados = 0;
        long edificiosCreados = 0;
        long aulasCreadas = 0;
    }
}
