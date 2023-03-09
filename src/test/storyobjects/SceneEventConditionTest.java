package storyobjects;

import model.storyobjects.SceneEventCondition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Deserializer;

import static org.junit.jupiter.api.Assertions.*;

public class SceneEventConditionTest {

    private SceneEventCondition testSceneEventCond1;
    private SceneEventCondition testSceneEventCond2;

    @BeforeAll
    static void init() {
        Deserializer.initializeGson();
    }

    @BeforeEach
    void setup() {
        testSceneEventCond1 = Deserializer.loadObject(SceneEventCondition.class,
                "data/test/scene_event_condition_1.json");
        testSceneEventCond2 = Deserializer.loadObject(SceneEventCondition.class,
                "data/test/scene_event_condition_2.json");
    }

    @Test
    void testCreation() {
        assertEquals("visited_icer?", testSceneEventCond1.getKey());
        assertEquals("@t", testSceneEventCond1.getExpected());
        assertEquals("@missingSpell", testSceneEventCond2.getKey());
        assertEquals("brulez", testSceneEventCond2.getExpected());
    }

    @Test
    void testEquals() {
        assertFalse(testSceneEventCond1.equals(null));
        assertFalse(testSceneEventCond1.equals("String"));
        assertFalse(testSceneEventCond1.equals(testSceneEventCond2));
        assertFalse(testSceneEventCond1.equals(new SceneEventCondition(" ", "@t")));
        assertFalse(testSceneEventCond2.equals(new SceneEventCondition("@missingSpell", " ")));
        assertTrue(testSceneEventCond1.equals(testSceneEventCond1));
    }

    @Test
    void testHashCode() {
        assertEquals(testSceneEventCond1.hashCode(), testSceneEventCond1.hashCode());
    }
}
