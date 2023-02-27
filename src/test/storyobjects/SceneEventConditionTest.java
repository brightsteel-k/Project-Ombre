package storyobjects;

import model.storyobjects.Location;
import model.storyobjects.SceneEventCondition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Deserializer;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        assertEquals("explored_icer", testSceneEventCond1.getKey());
        assertEquals("true", testSceneEventCond1.getExpected());
        assertEquals("@missingSpell", testSceneEventCond2.getKey());
        assertEquals("brulez", testSceneEventCond2.getExpected());
    }

    @Test
    void testEquals() {
        assertNotEquals(testSceneEventCond1, "String");
        assertNotEquals(testSceneEventCond1, testSceneEventCond2);
        assertEquals(testSceneEventCond1, testSceneEventCond1);
    }
}
