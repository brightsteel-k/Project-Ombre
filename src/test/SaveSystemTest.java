import model.Player;
import model.SaveSystem;
import model.StoryController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Deserializer;

import static org.junit.jupiter.api.Assertions.*;

class SaveSystemTest {

    SaveSystem testSaveSystem;
    static Player testPlayer;

    @BeforeAll
    static void beforeAll() {
        StoryController story = new StoryController();
        testPlayer = new Player();
        testPlayer.addItem("malachite");
        testPlayer.addSpell("gelez");
        testPlayer.setCondition("thing", "@t");
    }

    @BeforeEach
    void setUp() {
        testSaveSystem = new SaveSystem();
        testSaveSystem.deleteSave();
    }

    @Test
    void testSaveAndLoadGame() {
        testSaveSystem.saveGame(new Player(), "a", "b");
        testSaveSystem.loadGame();
        Player testPlayer2 = testSaveSystem.getPlayer();
        assertFalse(testPlayer2.hasItem("malachite"));
        assertFalse(testPlayer2.hasSpell("gelez"));
        assertFalse(testPlayer2.conditionMet("thing", "@t"));
        assertTrue(testPlayer2.conditionMet("other_thing", "@f"));
        assertEquals("a", testSaveSystem.getCurrentScene());
        assertEquals("b", testSaveSystem.getCurrentLocation());
        makeTestSave();
        testSaveSystem.loadGame();
        testPlayer2 = testSaveSystem.getPlayer();
        assertTrue(testPlayer2.hasItem("malachite"));
        assertTrue(testPlayer2.hasSpell("gelez"));
        assertTrue(testPlayer2.conditionMet("thing", "@t"));
        assertTrue(testPlayer2.conditionMet("other_thing", "@f"));
        assertEquals("scene", testSaveSystem.getCurrentScene());
        assertEquals("location", testSaveSystem.getCurrentLocation());
    }

    @Test
    void testDeleteSave() {
        testSaveSystem.deleteSave();
        assertFalse(testSaveSystem.isSaveDetected());
        makeTestSave();
        testSaveSystem = new SaveSystem();
        assertTrue(testSaveSystem.isSaveDetected());
        testSaveSystem.deleteSave();
        testSaveSystem = new SaveSystem();
        assertFalse(testSaveSystem.isSaveDetected());
    }

    private void makeTestSave() {
        testSaveSystem.saveGame(testPlayer, "scene", "location");
    }
}