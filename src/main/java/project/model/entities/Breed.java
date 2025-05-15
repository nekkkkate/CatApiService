package project.model.entities;

import jakarta.persistence.*;
import lombok.*;
import project.model.entities.collections.Breeds;

@Setter
@Getter
@Entity
@Table(name = "breeds")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Breed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, unique = true)
    private Breeds type;
}