package storyobjects;

import model.storyobjects.SceneEventCondition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Deserializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SceneEventConditionTest {

    private SceneEventCondition testSceneEventCond1;
    private SceneEventCondition testSceneEventCond2;

    @BeforeAll
    static void init() {
        if (!Deserializer.hasGson()) {
            Deserializer.initializeGson();
        }
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
}
