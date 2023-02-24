package storyobjects;

import model.storyobjects.Spell;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Deserializer;

import static org.junit.jupiter.api.Assertions.*;

public class SpellTest {

    Spell testSpell;

    @BeforeAll
    static void init() {
        if (!Deserializer.hasGson()) {
            Deserializer.initializeGson();
        }
    }

    @BeforeEach
    void setup() {
        testSpell = Deserializer.loadObject(Spell.class, "data/test/spell.json");
    }

    @Test
    void testCreation() {
        assertEquals("souvenez-vous", testSpell.getIncantation());
        assertEquals(0.5f, testSpell.getDamage());
    }
}
