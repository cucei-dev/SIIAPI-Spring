package dev.cucei.siiapi.modules.profesor;

import jakarta.persistence.*;
import lombok.*;

/**
 * Professor entity.
 */
@Entity
@Table(name = "profesor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
