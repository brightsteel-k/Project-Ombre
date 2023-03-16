package model;

import model.Player;
import model.StoryController;
import model.storyobjects.Spell;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DataManager;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    Player testPlayer;
    static Spell testSpell;

    @BeforeAll
    static void beforeAll() {
        DataManager.initializeGson();
        testSpell = DataManager.loadObject(Spell.class, "data/spells/brulez.json");
        StoryController story = new StoryController();
    }

    @BeforeEach
    void setup() {
        testPlayer = new Player();
    }

    @Test
    void testItems() {
        testPlayer.addItem("tanzanite");
        testPlayer.addItem("oridur_ingot");
        testPlayer.addItem("oridur_ingot");
        assertTrue(testPlayer.hasItem("tanzanite"));
        assertTrue(testPlayer.hasItem("oridur_ingot"));
        assertFalse(testPlayer.hasItem("emerald_dagger"));
    }

    @Test
    void testSpells() {
        testPlayer.addSpell("oubliez");
        testPlayer.addSpell("oubliez");
        testPlayer.addSpell("ralentissez");
        assertTrue(testPlayer.hasSpell("oubliez"));
        assertTrue(testPlayer.hasSpell("ralentissez"));
        assertFalse(testPlayer.hasSpell("souffrez"));
    }

    @Test
    void testConditions() {
        assertFalse(testPlayer.conditionMet("visited_magnetar?", "@t"));
        assertTrue(testPlayer.conditionMet("visited_black_hole?", "@f"));
        testPlayer.setCondition("chose_weapon", "plasma_sword");
        testPlayer.setCondition("chose_armour", "in'cassamere");
        testPlayer.setCondition("visited_magnetar?", "@f");
        assertTrue(testPlayer.conditionMet("chose_weapon", "plasma_sword"));
        assertFalse(testPlayer.conditionMet("chose_armour", "diamond"));
        assertTrue(testPlayer.conditionMet("visited_magnetar?", "@f"));
    }

    @Test
    void testGetSpells() {
        assertTrue(Arrays.equals(new Spell[0], testPlayer.getSpells()));
        testPlayer.addSpell("oubliez");
        testPlayer.addSpell("brulez");
        testPlayer.addSpell("souffrez");
        testPlayer.addSpell("brulez");
        Spell[] expected = new Spell[] { null, testSpell, null };
        assertTrue(Arrays.equals(expected, testPlayer.getSpells()));
    }
}
