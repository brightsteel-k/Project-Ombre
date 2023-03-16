package model;

import model.storyobjects.Spell;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DataManager;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player testPlayer1;
    private Player testPlayer2;
    private static Spell testSpell;

    @BeforeAll
    static void beforeAll() {
        DataManager.initializeGson();
        testSpell = new Spell("Burn", "brulez", "ember",
                "Summons a swirling ray of blazing flame.", 4f);
        StoryController story = new StoryController();
    }

    @BeforeEach
    void setup() {
        testPlayer1 = new Player();
    }

    @Test
    void testConstructor() {
        Map<String, String> progressConditions = new HashMap<>();
        progressConditions.put("visited_black_dwarf?", "@t");
        List<String> items = new ArrayList<>();
        items.add("malachite");
        Map<String, Spell> spells = new HashMap<>();
        Spell recall = new Spell("Recall", "souvenez-vous", "silk",
                "Brings other spells to the forefront of the target's mind.", 0.5f);
        spells.put("souvenez-vous", recall);
        testPlayer2 = new Player(progressConditions, items, spells);

        assertEquals(progressConditions, testPlayer2.getProgressConditions());
        assertEquals(items, testPlayer2.getItems());
        assertTrue(Arrays.equals(new Spell[] { recall }, testPlayer2.getSpells()));
    }

    @Test
    void testItems() {
        testPlayer1.addItem("tanzanite");
        testPlayer1.addItem("oridur_ingot");
        testPlayer1.addItem("oridur_ingot");
        assertTrue(testPlayer1.hasItem("tanzanite"));
        assertTrue(testPlayer1.hasItem("oridur_ingot"));
        assertFalse(testPlayer1.hasItem("emerald_dagger"));
    }

    @Test
    void testSpells() {
        testPlayer1.addSpell("oubliez");
        testPlayer1.addSpell("oubliez");
        testPlayer1.addSpell("ralentissez");
        assertTrue(testPlayer1.hasSpell("oubliez"));
        assertTrue(testPlayer1.hasSpell("ralentissez"));
        assertFalse(testPlayer1.hasSpell("souffrez"));
    }

    @Test
    void testConditions() {
        assertFalse(testPlayer1.conditionMet("visited_magnetar?", "@t"));
        assertTrue(testPlayer1.conditionMet("visited_black_hole?", "@f"));
        testPlayer1.setCondition("chose_weapon", "plasma_sword");
        testPlayer1.setCondition("chose_armour", "in'cassamere");
        testPlayer1.setCondition("visited_magnetar?", "@f");
        assertTrue(testPlayer1.conditionMet("chose_weapon", "plasma_sword"));
        assertFalse(testPlayer1.conditionMet("chose_armour", "diamond"));
        assertTrue(testPlayer1.conditionMet("visited_magnetar?", "@f"));
    }

    @Test
    void testGetSpells() {
        assertTrue(Arrays.equals(new Spell[0], testPlayer1.getSpells()));
        testPlayer1.addSpell("qweqwe");
        testPlayer1.addSpell("brulez");
        testPlayer1.addSpell("werert");
        testPlayer1.addSpell("brulez");
        Spell[] expected = new Spell[] { null, testSpell, null };
        assertTrue(Arrays.equals(expected, testPlayer1.getSpells()));
    }
}
