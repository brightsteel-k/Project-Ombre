import model.Player;
import model.SaveSystem;
import model.StoryController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Deserializer;

import static org.junit.jupiter.api.Assertions.*;

public class SaveSystemTest {

    SaveSystem testSaveSystem;
    static Player testPlayer;

    @BeforeAll
    public static void beforeAll() {
        Deserializer.initializeGson();
        StoryController story = new StoryController();
        testPlayer = new Player();
        testPlayer.addItem("malachite");
        testPlayer.addSpell("gelez");
        testPlayer.setCondition("thing", "@t");
    }

    @BeforeEach
    void setup() {
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
        makeTestSaveAndLoad();
        testPlayer2 = testSaveSystem.getPlayer();
        assertTrue(testPlayer2.hasItem("malachite"));
        assertTrue(testPlayer2.hasSpell("gelez"));
        assertTrue(testPlayer2.conditionMet("thing", "@t"));
        assertTrue(testPlayer2.conditionMet("other_thing", "@f"));
        assertEquals("scene", testSaveSystem.getCurrentScene());
        assertEquals("location", testSaveSystem.getCurrentLocation());
    }

    @Test
    void testGetPlayer() {
        makeTestSaveAndLoad();
        Player testPlayer2 = testSaveSystem.getPlayer();
        assertTrue(testPlayer2.hasItem("malachite"));
        assertTrue(testPlayer2.hasSpell("gelez"));
        assertTrue(testPlayer2.conditionMet("thing", "@t"));
        assertTrue(testPlayer2.conditionMet("other_thing", "@f"));
    }

    @Test
    void testGetCurrentScene() {
        makeTestSaveAndLoad();
        assertEquals("scene", testSaveSystem.getCurrentScene());
    }

    @Test
    void testGetCurrentLocation() {
        makeTestSaveAndLoad();
        assertEquals("location", testSaveSystem.getCurrentLocation());
    }

    @Test
    void testDeleteSave() {
        testSaveSystem.deleteSave();
        assertFalse(testSaveSystem.isSaveDetected());
        makeTestSaveAndLoad();
        assertTrue(testSaveSystem.isSaveDetected());
        testSaveSystem.deleteSave();
        assertFalse(testSaveSystem.isSaveDetected());
    }

    private void makeTestSaveAndLoad() {
        try {
            testSaveSystem.saveGame(testPlayer, "scene", "location");
        } catch (RuntimeException e) {
            // fail("SaveSystem.saveGame() could not run - " + e.getMessage());
        }
        try {
            testSaveSystem.loadGame();
        } catch (RuntimeException e) {
            // fail("SaveSystem.loadGame() could not run - " + e.getMessage());
        }
    }


    @Test
    void testSaveStateConstructor() {
        SaveSystem.SaveState state = testSaveSystem.new SaveState(testPlayer, "string1", "string2");
        Player testPlayer2 = state.getPlayer();
        assertTrue(testPlayer2.hasItem("malachite"));
        assertTrue(testPlayer2.hasSpell("gelez"));
        assertTrue(testPlayer2.conditionMet("thing", "@t"));
        assertTrue(testPlayer2.conditionMet("other_thing", "@f"));
        assertEquals("string1", state.getCurrentScene());
        assertEquals("string2", state.getCurrentLocation());
    }
}