package dev.cucei.siiapi.modules.edificio;

import dev.cucei.siiapi.modules.centro.CentroUniversitario;
import jakarta.persistence.*;
import lombok.*;

/**
 * Building entity within a university center.
 */
@Entity
@Table(name = "edificio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Edificio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro_id", nullable = false)
    private CentroUniversitario centro;
}
