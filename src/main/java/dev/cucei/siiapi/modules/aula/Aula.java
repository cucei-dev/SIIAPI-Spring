package dev.cucei.siiapi.modules.aula;

import dev.cucei.siiapi.modules.edificio.Edificio;
import jakarta.persistence.*;
import lombok.*;

/**
 * Classroom entity within a building.
 */
@Entity
@Table(name = "aula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edificio_id", nullable = false)
    private Edificio edificio;
}
