package storyobjects;

import model.storyobjects.SceneEvent;
import model.storyobjects.SceneEventCondition;
import model.storyobjects.SceneEventType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Deserializer;

import static org.junit.jupiter.api.Assertions.*;

public class SceneEventTest {

    private SceneEvent testSceneEvent1;
    private SceneEvent testSceneEvent2;
    private SceneEvent testSceneEvent3;
    private SceneEvent testSceneEvent4;

    private SceneEventCondition sceneEventCond1;
    private SceneEventCondition sceneEventCond2;

    @BeforeAll
    static void init() {
        if (!Deserializer.hasGson()) {
            Deserializer.initializeGson();
        }
    }

    @BeforeEach
    void setup() {
        sceneEventCond1 = Deserializer.loadObject(SceneEventCondition.class,
                "data/test/scene_event_condition_1.json");
        sceneEventCond2 = Deserializer.loadObject(SceneEventCondition.class,
                "data/test/scene_event_condition_1.json");
        testSceneEvent1 = new SceneEvent(SceneEventType.START_EXPLORING);
        testSceneEvent2 = new SceneEvent(SceneEventType.ACQUIRE_ITEM, "oridur_ingot");
        testSceneEvent3 = new SceneEvent(SceneEventType.START_EXPLORING,
                new SceneEventCondition[] { sceneEventCond1 });
        testSceneEvent4 = new SceneEvent(SceneEventType.LEARN_SPELL, "gelez",
                new SceneEventCondition[] { sceneEventCond1, sceneEventCond2 });
    }

    @Test
    void testConstructors() {

        assertEquals(SceneEventType.START_EXPLORING, testSceneEvent1.getType());
        assertNull(testSceneEvent1.getKeyword());
        assertNull(testSceneEvent1.getConditions());

        assertEquals(SceneEventType.ACQUIRE_ITEM, testSceneEvent2.getType());
        assertEquals("oridur_ingot", testSceneEvent2.getKeyword());
        assertNull(testSceneEvent2.getConditions());

        assertEquals(SceneEventType.START_EXPLORING, testSceneEvent3.getType());
        assertNull(testSceneEvent3.getKeyword());
        SceneEventCondition[] actual = testSceneEvent3.getConditions();
        assertEquals(1, actual.length);
        assertEquals(sceneEventCond1, actual[0]);

        assertEquals(SceneEventType.LEARN_SPELL, testSceneEvent4.getType());
        assertEquals("gelez", testSceneEvent4.getKeyword());
        actual = testSceneEvent4.getConditions();
        assertEquals(2, actual.length);
        assertEquals(sceneEventCond1, actual[0]);
        assertEquals(sceneEventCond2, actual[1]);
    }

    @Test
    void testIsType() {
        assertTrue(testSceneEvent1.isType(SceneEventType.START_EXPLORING));
        assertFalse(testSceneEvent2.isType(SceneEventType.CHANGE_LOCATION));
        assertFalse(testSceneEvent3.isType(SceneEventType.ACQUIRE_ITEM));
        assertTrue(testSceneEvent4.isType(SceneEventType.LEARN_SPELL));
    }

    @Test
    void testHasConditions() {
        assertFalse(testSceneEvent1.hasConditions());
        assertFalse(testSceneEvent2.hasConditions());
        assertTrue(testSceneEvent3.hasConditions());
        assertTrue(testSceneEvent4.hasConditions());
    }
}
