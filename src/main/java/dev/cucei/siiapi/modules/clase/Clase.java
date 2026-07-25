package dev.cucei.siiapi.modules.clase;

import dev.cucei.siiapi.modules.aula.Aula;
import dev.cucei.siiapi.modules.seccion.Seccion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

/**
 * Class entity representing a specific time slot within a section.
 */
@Entity
@Table(name = "clase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sesion;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    private Integer dia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seccion_id", nullable = false)
    private Seccion seccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aula_id")
    private Aula aula;
}
