package project.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@Entity
@Table(name = "owners")
@AllArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String name;

    private LocalDate birthdate;

    public void addCat(Cat cat) {
        cat.setOwner(this);
    }

    public void removeCat(Cat cat) {
        cat.setOwner(null);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner owner)) return false;
        return Objects.equals(id, owner.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}