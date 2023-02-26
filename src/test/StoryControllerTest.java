import exceptions.InvalidLocationException;
import exceptions.InvalidSceneException;
import model.Player;
import model.StoryController;
import model.storyobjects.Location;
import model.storyobjects.Scene;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Deserializer;

import static org.junit.jupiter.api.Assertions.*;

public class StoryControllerTest {

    Player testPlayer = new Player();
    StoryController testStory;

    @BeforeAll
    static void init() {
        Deserializer.initializeGson();
    }

    @BeforeEach
    void setup() {
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
            assertEquals(Deserializer.loadObject(Location.class, "data/locations/front.json"),
                    testStory.getCurrentLocation());
        } catch (InvalidLocationException e1) {
            fail();
        }

        try {
            testStory.setCurrentLocation("IO");
            fail();
        } catch (InvalidLocationException e2) {

        }
    }
}
