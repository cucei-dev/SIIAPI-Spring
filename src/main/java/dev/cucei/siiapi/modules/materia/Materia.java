package dev.cucei.siiapi.modules.materia;

import jakarta.persistence.*;
import lombok.*;

/**
 * Subject/course entity (e.g., "TC1024 - Programacion").
 */
@Entity
@Table(name = "materia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int creditos;

    @Column(nullable = false, unique = true)
    private String clave;
}
