package storyobjects;

import exceptions.InvalidActionException;
import model.storyobjects.Location;
import model.storyobjects.SceneEvent;
import model.storyobjects.SceneEventType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Deserializer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    private Location testLocation;

    @BeforeAll
    static void init() {
        Deserializer.initializeGson();
    }

    @BeforeEach
    void setup() {
        testLocation = Deserializer.loadObject(Location.class, "data/test/location.json");
    }

    @Test
    void testCreation() {
        assertEquals("the Azyrean armoury", testLocation.getName());
    }

    @Test
    void testGetObjectOfInterest() {
        assertEquals("sword", testLocation.getObjectOfInterest("longsword"));
        assertEquals("sword", testLocation.getObjectOfInterest("broadsword"));
        assertEquals("dagger", testLocation.getObjectOfInterest("dagger"));
        assertEquals("dagger", testLocation.getObjectOfInterest("knife"));
    }

    @Test
    void testTryActionCode() {
        try {
            List<SceneEvent> gotoEvents = testLocation.tryActionCode("goto@throneroom");
            assertEquals(1, gotoEvents.size());
            assertEquals(new SceneEvent(SceneEventType.CHANGE_LOCATION, "throneroom"),
                    gotoEvents.get(0));

            List<SceneEvent> takeEvents = testLocation.tryActionCode("take@polearm");
            assertEquals(2, takeEvents.size());
            assertEquals(new SceneEvent(SceneEventType.DISPLAY_TEXT, "You eagerly take the polearm."),
                    takeEvents.get(0));
            assertEquals(new SceneEvent(SceneEventType.ACQUIRE_ITEM, "polearm"),
                    takeEvents.get(1));
        } catch (InvalidActionException e1) {
            fail();
        }

        try {
            testLocation.tryActionCode("take@lightsaber");
            fail();
        } catch (InvalidActionException e2) {
            assertSame(InvalidActionException.class, e2.getClass());
        }
    }

    @Test
    void testEquals() {
        assertFalse(testLocation.equals("String"));
        assertFalse(testLocation.equals(new Location()));
        assertTrue(testLocation.equals(testLocation));
    }
}
