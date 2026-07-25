package dev.cucei.siiapi.modules.calendario;

import jakarta.persistence.*;
import lombok.*;

/**
 * Academic calendar entity (e.g., "2025-1").
 */
@Entity
@Table(name = "calendario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calendario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String siiauId;
}
