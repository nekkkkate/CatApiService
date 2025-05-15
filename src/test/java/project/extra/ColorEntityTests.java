package project.extra;

import project.model.entities.Color;
import project.model.entities.collections.Colors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColorEntityTests {

    @Test
    void colorEntity_ShouldWorkWithBuilder() {
        Color color = Color.builder()
                .id(1)
                .type(Colors.BROWN)
                .build();

        assertEquals(1, color.getId());
        assertEquals(Colors.BROWN, color.getType());
    }

    @Test
    void colorEntity_ShouldWorkWithConstructor() {
        Color color = new Color(Colors.ORANGE);

        assertNull(color.getId());
        assertEquals(Colors.ORANGE, color.getType());
    }

    @Test
    void colorEntity_ShouldHaveSetters() {
        Color color = new Color();
        color.setType(Colors.WHITE);

        assertEquals(Colors.WHITE, color.getType());
    }
}