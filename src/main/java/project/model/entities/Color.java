package project.model.entities;

import jakarta.persistence.*;
import lombok.*;
import project.model.entities.collections.Colors;

@Setter
@Getter
@Entity
@Table(name = "colors")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, unique = true)
    private Colors type;

    public Color(Colors type) {
        this.type = type;
    }
}