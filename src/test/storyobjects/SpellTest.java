package storyobjects;

import model.storyobjects.Spell;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Deserializer;

import static org.junit.jupiter.api.Assertions.*;

public class SpellTest {

    Spell testSpell1;
    Spell testSpell2;
    Spell testSpell3;
    Spell testSpell4;

    @BeforeAll
    static void init() {
        Deserializer.initializeGson();
    }

    @BeforeEach
    void setup() {
        testSpell1 = Deserializer.loadObject(Spell.class, "data/test/spell_1.json");
        testSpell2 = Deserializer.loadObject(Spell.class, "data/test/spell_2.json");
        testSpell3 = Deserializer.loadObject(Spell.class, "data/test/spell_3.json");
        testSpell4 = Deserializer.loadObject(Spell.class, "data/test/spell_4.json");
    }

    @Test
    void testCreation() {
        assertEquals("souvenez-vous", testSpell1.getIncantation());
        assertEquals(0.5f, testSpell1.getDamage());
        assertEquals("souffrez", testSpell3.getIncantation());
        assertEquals(4f, testSpell3.getDamage());
    }

    @Test
    void testEquals() {
        assertFalse(testSpell1.equals(null));
        assertFalse(testSpell1.equals("String"));
        assertFalse(testSpell1.equals(new Spell()));
        assertFalse(testSpell1.equals(testSpell2));
        assertFalse(testSpell1.equals(testSpell3));
        assertFalse(testSpell2.equals(testSpell3));
        assertTrue(testSpell1.equals(testSpell4));
        assertTrue(testSpell1.equals(testSpell1));
    }

    @Test
    void testHashCode() {
        assertEquals(-512112168, testSpell1.hashCode());
    }
}
