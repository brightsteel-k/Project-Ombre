package storyobjects;

import exceptions.SceneEndingException;
import model.storyobjects.Scene;
import model.storyobjects.SceneEvent;
import model.storyobjects.SceneEventType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Deserializer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SceneTest {

    private Scene testScene;

    @BeforeAll
    static void init() {
        Deserializer.initializeGson();
    }

    @BeforeEach
    void setup() {
        testScene = Deserializer.loadObject(Scene.class, "data/test/scene_1.json");
        testScene.startScene();
    }

    @Test
    void testNextLineStart() {
        try {
            assertEquals("A flicker of arcane light.", testScene.getNextLine());
            assertEquals("Punctured by the black edge of a falchion.", testScene.getNextLine());
            assertEquals("\"Do not think that you are stronger than me just because you sparked one more band.\"", testScene.getNextLine());
        } catch (SceneEndingException e) {
            fail();
        }
    }

    @Test
    void testEndNextScene() {
        try {
            for (int i = 0; i < 9; i++) {
                testScene.getNextLine();
            }
        } catch (SceneEndingException e) {
            fail();
        }

        try {
            testScene.getNextLine();
            fail();
        } catch (SceneEndingException e) {
            assertEquals(e.getNextScene(), "destruction");
            assertFalse(e.shouldStartExploring());
            List<SceneEvent> endSceneEvents = e.getEvents();
            assertEquals(1, endSceneEvents.size());
            assertEquals(new SceneEvent(SceneEventType.NEXT_SCENE, "destruction"), endSceneEvents.get(0));
        }
    }

    @Test
    void testEndStartExploring() {
        Scene testScene2 = Deserializer.loadObject(Scene.class, "data/test/scene_2.json");
        testScene2.startScene();
        try {
            testScene2.getNextLine();
            fail();
        } catch (SceneEndingException e) {
            assertTrue(e.shouldStartExploring());
            assertNull(e.getNextScene());
            List<SceneEvent> endSceneEvents = e.getEvents();
            assertEquals(2, endSceneEvents.size());
            assertEquals(new SceneEvent(SceneEventType.LEARN_SPELL, "brulez"), endSceneEvents.get(0));
            assertEquals(new SceneEvent(SceneEventType.START_EXPLORING), endSceneEvents.get(1));
        }
    }
}
