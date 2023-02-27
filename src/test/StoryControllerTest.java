import exceptions.*;
import model.Player;
import model.StoryController;
import model.storyobjects.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Deserializer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoryControllerTest {

    private StoryController testStory;
    private Player testPlayer;
    private static SceneEventCondition cond1;
    private static SceneEventCondition cond2;
    private static SceneEventCondition cond3;
    private static SceneEventCondition cond4;
    private static SceneEventCondition cond5;

    @BeforeAll
    static void init() {
        Deserializer.initializeGson();
        cond1 = new SceneEventCondition("custom_key", "@t");
        cond2 = new SceneEventCondition("@hasItem", "obsidian");
        cond3 = new SceneEventCondition("@missingItem", "pearl");
        cond4 = new SceneEventCondition("@hasSpell", "tombez");
        cond5 = new SceneEventCondition("@missingSpell", "riez");
    }

    @BeforeEach
    void setup() {
        testPlayer = new Player();
        testStory = new StoryController(testPlayer);
    }

    @Test
    void testSetCurrentScene() {
        try {
            testStory.setCurrentScene("home");
            assertEquals(Deserializer.loadObject(Scene.class, "data/scenes/home.json"),
                    testStory.getCurrentScene());
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
        assertNextLineEndsScene("home", false, testStory);
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
    void testConditionsFulfilled() {
        SceneEventCondition[] conditions1 = new SceneEventCondition[] { cond1 };
        SceneEventCondition[] conditions2 = new SceneEventCondition[] { cond3, cond5, cond3 };

        assertFalse(testStory.conditionsFulfilled(conditions1));
        assertTrue(testStory.conditionsFulfilled(conditions2));
    }

    @Test
    void testConditionFulfilled() {
        testPlayer.setCondition("custom_key", "@t");
        testPlayer.addSpell("riez");
        testPlayer.addSpell("tombez");
        testPlayer.addItem("pearl");
        assertTrue(testStory.conditionFulfilled(cond1));
        assertFalse(testStory.conditionFulfilled(cond2));
        assertFalse(testStory.conditionFulfilled(cond3));
        assertTrue(testStory.conditionFulfilled(cond4));
        assertFalse(testStory.conditionFulfilled(cond5));
    }

    private void assertNextLine(String expected, StoryController story) {
        try {
            assertEquals(expected, story.getCurrentNextLine());
        } catch (SceneEndingException e) {
            fail();
        }
    }

    private void assertNextLineEndsScene(String nextScene, boolean shouldExplore, StoryController story) {
        try {
            testStory.getCurrentNextLine();
            fail();
        } catch (SceneEndingException e) {
            if (e.shouldStartExploring() != shouldExplore || !e.getNextScene().equals(nextScene)) {
                fail();
            }
        }
    }

    private void assertInvalidAction(StoryController story, String[] input) {
        try {
            story.executeAction(input);
            fail();
        } catch (InvalidActionException e) {
            assertSame(InvalidActionException.class, e.getClass());
        }
    }

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
