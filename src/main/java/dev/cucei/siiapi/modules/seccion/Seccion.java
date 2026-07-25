package dev.cucei.siiapi.modules.seccion;

import dev.cucei.siiapi.modules.calendario.Calendario;
import dev.cucei.siiapi.modules.centro.CentroUniversitario;
import dev.cucei.siiapi.modules.materia.Materia;
import dev.cucei.siiapi.modules.profesor.Profesor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Section entity representing a course offering in a specific calendar.
 */
@Entity
@Table(name = "seccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nrc;

    @Column(nullable = false)
    private int cupos;

    @Column(nullable = false)
    private int cuposDisponibles;

    private String est;

    private LocalDateTime periodoInicio;

    private LocalDateTime periodoFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro_id", nullable = false)
    private CentroUniversitario centro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendario_id", nullable = false)
    private Calendario calendario;
}
