package model.storyobjects;

import model.storyobjects.Spell;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DataManager;

import static org.junit.jupiter.api.Assertions.*;

public class SpellTest {

    Spell testSpell1;
    Spell testSpell2;

    @BeforeAll
    static void init() {
        DataManager.initializeGson();
    }

    @BeforeEach
    void setup() {
        testSpell1 = new Spell("Recall", "souvenez-vous", "silk",
                "Brings other spells to the forefront of the target's mind.", 0.5f);
        testSpell2 = new Spell("Suffer", "souffrez", "iron",
                "Inflicts massive pain to the target's internal nerves.", 4f);
    }

    @Test
    void testCreation() {
        assertEquals("Recall", testSpell1.getName());
        assertEquals("souvenez-vous", testSpell1.getIncantation());
        assertEquals("silk", testSpell1.getSchool());
        assertEquals("Brings other spells to the forefront of the target's mind.", testSpell1.getDescription());
        assertEquals(0.5f, testSpell1.getDamage());
    }

    @Test
    void testEquals() {
        assertFalse(testSpell2.equals(null));
        assertFalse(testSpell2.equals("String"));
        assertTrue(testSpell2.equals(testSpell2));
        assertTrue(testSpell2.equals(new Spell("Inflict Suffering", "souffrez", "iron",
                "Twists the target's internal organs in very painful ways.", 5f)));
        assertFalse(testSpell2.equals(testSpell1));
    }

    @Test
    void testHashCode() {
        assertEquals(-189162712, testSpell1.hashCode());
    }
}
