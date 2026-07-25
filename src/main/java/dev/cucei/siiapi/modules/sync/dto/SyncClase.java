package dev.cucei.siiapi.modules.sync.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO representing a class schedule entry from SIIAU.
 */
public record SyncClase(
    String sesion,
    String horaInicio,
    String horaFin,
    Integer dia,
    String edificio,
    String aula
) {
}
