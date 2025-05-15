package project.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "cats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cat_name", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "cat_birthday")
    private LocalDate birthDate;

    @Setter
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Breed breed;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToMany
    @JoinTable(name = "cat_friends", joinColumns = @JoinColumn(name = "cat_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
    @Builder.Default
    private List<Cat> catsFriends = new ArrayList<>();

    public void addFriend(Cat friend) {
        if (!catsFriends.contains(friend)) {
            catsFriends.add(friend);
            friend.getCatsFriends().add(this);
        }
    }

    public void removeFriend(Cat friend) {
        catsFriends.remove(friend);
        friend.getCatsFriends().remove(this);
    }
}