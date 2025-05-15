package project.extra;

import project.model.entities.Cat;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CatEntityTests {

    @Test
    void addFriend_ShouldAddBothDirections() {
        Cat cat1 = new Cat();
        Cat cat2 = new Cat();

        cat1.addFriend(cat2);

        assertTrue(cat1.getCatsFriends().contains(cat2));
        assertTrue(cat2.getCatsFriends().contains(cat1));
    }

    @Test
    void addFriend_ShouldNotAddDuplicate() {
        Cat cat1 = new Cat();
        Cat cat2 = new Cat();

        cat1.addFriend(cat2);
        cat1.addFriend(cat2);

        assertEquals(1, cat1.getCatsFriends().size());
    }

    @Test
    void removeFriend_ShouldRemoveBothDirections() {
        Cat cat1 = new Cat();
        Cat cat2 = new Cat();
        cat1.addFriend(cat2);

        cat1.removeFriend(cat2);

        assertFalse(cat1.getCatsFriends().contains(cat2));
        assertFalse(cat2.getCatsFriends().contains(cat1));
    }

    @Test
    void builder_ShouldWorkCorrectly() {
        Cat cat = Cat.builder()
                .id(1)
                .name("Fluffy")
                .birthDate(LocalDate.of(2020, 5, 15))
                .build();

        assertEquals(1, cat.getId());
        assertEquals("Fluffy", cat.getName());
        assertEquals(LocalDate.of(2020, 5, 15), cat.getBirthDate());
    }
}