package model;

import exceptions.*;
import model.Player;
import model.SaveSystem;
import model.StoryController;
import model.storyobjects.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DataManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoryControllerTest {

    private StoryController testStory;
    private static SceneEventCondition cond1;
    private static SceneEventCondition cond2;
    private static SceneEventCondition cond3;
    private static SceneEventCondition cond4;
    private static SceneEventCondition cond5;

    @BeforeAll
    static void init() {
        DataManager.initializeGson();
        cond1 = new SceneEventCondition("custom_key", "@t");
        cond2 = new SceneEventCondition("@hasItem", "obsidian");
        cond3 = new SceneEventCondition("@missingItem", "pearl");
        cond4 = new SceneEventCondition("@hasSpell", "tombez");
        cond5 = new SceneEventCondition("@missingSpell", "riez");
    }

    @BeforeEach
    void setup() {
        testStory = new StoryController();
        testStory.setPlayer(new Player());
    }

    @Test
    void testSetCurrentScene() {
        try {
            testStory.setCurrentScene("home");
            assertEquals(DataManager.loadObject(Scene.class, "data/scenes/home.json"),
                    testStory.getCurrentScene());
            assertEquals("home", testStory.getCurrentSceneId());
        } catch (InvalidSceneException e1) {
            fail();
        }

        try {
            testStory.setCurrentScene("GANYMEDE");
            fail();
        } catch (InvalidSceneException e2) {

        }
    }

    @Test
    void testSetCurrentLocation() {
        try {
            testStory.setCurrentLocation("front");
            assertEquals("the front of the room", testStory.getCurrentLocation().getName());
            testStory.setCurrentLocation("test_location");
            assertEquals("the Azyrean armoury", testStory.getCurrentLocation().getName());
            testStory.setCurrentLocation("worktable");
            assertEquals("the worktable", testStory.getCurrentLocation().getName());
        } catch (InvalidLocationException e1) {
            fail();
        }

        try {
            testStory.setCurrentLocation("IO");
            fail();
        } catch (InvalidLocationException e2) {

        }
    }

    @Test
    void testGetCurrentNextLine() {
        testStory.setCurrentScene("intro");
        assertNextLine("A flash of brilliant violet light blinds your vision, making your head swirl in a " +
                        "vortex of freezing amethyst. Your mind feels numb. You steel yourself \nand step into the " +
                        "cool stonebrick chamber, shielding your eyes from the shining purple crystal in front of " +
                        "you. A moment later, the blazing light fades \nand you're left blinking spots away at the " +
                        "front of a large heptagonal chamber. ",
                    testStory);
        assertNextLine("What were you doing, again?", testStory);
        assertNextLine("The giant crystal in front of you sputters, a shifting maelstrom of violet energy " +
                "and abyssal darkness mounted on a dark gray metal frame.", testStory);
        for (int i = 0; i < 4; i++) {
            try {
                testStory.getCurrentNextLine();
            } catch (SceneEndingException e) {
                fail();
            }
        }
        assertNextLineEndsScene(testStory);
    }

    @Test
    void testChangeLocation() {
        testStory.setCurrentLocation("front");
        assertEquals("You leave the front of the room and make your way to the worktable.",
                testStory.changeLocation("worktable"));
        assertEquals("You leave the worktable and make your way to the front of the room.",
                testStory.changeLocation("front"));
    }

    @Test
    void testExecuteActionSuccess() {
        testStory.setCurrentLocation("test_location");
        List<SceneEvent> expected = new ArrayList<>();
        expected.add(new SceneEvent(SceneEventType.CHANGE_LOCATION, "throneroom"));
        try {
            assertEquals(expected, testStory.executeAction(new String[] { "goto", "throne" }));
        } catch (InvalidActionException e) {
            fail();
        }
        expected.clear();
        expected.add(new SceneEvent(SceneEventType.DISPLAY_TEXT, "You discreetly pocket the dagger."));
        expected.add(new SceneEvent(SceneEventType.ACQUIRE_ITEM, "dagger"));
        try {
            assertEquals(expected, testStory.executeAction(new String[] { "take", "knife" }));
        } catch (InvalidActionException e) {
            fail();
        }
    }

    @Test
    void testExecuteActionFail() {
        testStory.setCurrentLocation("test_location");
        assertInvalidAction(testStory, new String[] { "goto" });
        assertInvalidActionSubject("pulsar", testStory, new String[] { "goto", "pulsar" });
        assertInvalidAction(testStory, new String[] { "vaporize", "throneroom" });
        assertInvalidActionSubject("supernova", testStory, new String[] { "view", "supernova" });
    }

    @Test
    void testWriteValuesToSaveSystem() {
        SaveSystem s = new SaveSystem();

        testStory.setCurrentScene("home");
        testStory.setCurrentLocation("front");
        Player p = new Player();
        p.addItem("obsidian_knife");
        testStory.setPlayer(p);
        p.addSpell("brulez");
        testStory.writeValuesToSaveSystem(s);

        s.loadGame();
        assertEquals("home", s.getCurrentScene());
        assertEquals("front", s.getCurrentLocation());
        assertTrue(s.getPlayer().hasItem("obsidian_knife"));
        assertTrue(s.getPlayer().hasSpell("brulez"));
    }

    @Test
    void testConditionsFulfilled() {
        SceneEventCondition[] conditions1 = new SceneEventCondition[] { cond1 };
        SceneEventCondition[] conditions2 = new SceneEventCondition[] { cond3, cond5, cond3 };

        assertFalse(testStory.conditionsFulfilled(conditions1));
        assertTrue(testStory.conditionsFulfilled(conditions2));
    }

    @Test
    void testConditionFulfilled() {
        testStory.getPlayer().setCondition("custom_key", "@t");
        testStory.getPlayer().addSpell("riez");
        testStory.getPlayer().addSpell("tombez");
        testStory.getPlayer().addItem("pearl");
        assertTrue(testStory.conditionFulfilled(cond1));
        assertFalse(testStory.conditionFulfilled(cond2));
        assertFalse(testStory.conditionFulfilled(cond3));
        assertTrue(testStory.conditionFulfilled(cond4));
        assertFalse(testStory.conditionFulfilled(cond5));
    }

    // EFFECTS: fails if the next line of the current scene in the given story does not match the given expected line
    private void assertNextLine(String expected, StoryController story) {
        try {
            assertEquals(expected, story.getCurrentNextLine());
        } catch (SceneEndingException e) {
            fail();
        }
    }

    // EFFECTS: fails if requesting the next line of the current scene in the given story does not throw a
    //          SceneEndingException.
    private void assertNextLineEndsScene(StoryController story) {
        try {
            testStory.getCurrentNextLine();
            fail();
        } catch (SceneEndingException e) {

        }
    }

    // EFFECTS: fails if attempting to execute an action on the given story with the given input does not
    //          throw an InvalidActionException.
    private void assertInvalidAction(StoryController story, String[] input) {
        try {
            story.executeAction(input);
            fail();
        } catch (InvalidActionException e) {
            assertSame(InvalidActionException.class, e.getClass());
        }
    }

    // EFFECTS: fails if attempting to execute an action on the given story with the given input does not
    //          throw an ActionSubjectException caused by the given expected subject.
    private void assertInvalidActionSubject(String expectedSubject, StoryController story, String[] input) {
        try {
            story.executeAction(input);
            fail();
        } catch (InvalidActionException e) {
            assertSame(ActionSubjectException.class, e.getClass());
            String invalidSubject = ((ActionSubjectException)e).getInvalidObject();
            assertEquals(invalidSubject, expectedSubject);
        }
    }
}
