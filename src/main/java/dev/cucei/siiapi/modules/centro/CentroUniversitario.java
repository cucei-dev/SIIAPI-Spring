package dev.cucei.siiapi.modules.centro;

import jakarta.persistence.*;
import lombok.*;

/**
 * University center entity (e.g., "CUCEI").
 */
@Entity
@Table(name = "centro_universitario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CentroUniversitario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String siiauId;
}
