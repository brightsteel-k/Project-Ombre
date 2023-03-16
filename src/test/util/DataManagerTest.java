package util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class DataManagerTest {

    @BeforeAll
    static void setup() {
        DataManager.initializeGson();
    }

    @Test
    void testConstructor() {
        DataManager deserializer = new DataManager();
    }

    // Testing a special case for an internal method, since all public methods and other cases are implicitly used
    // and verified in other files' tests.
    @Test
    void testInvalidReadFile() {
        try {
            DataManager.loadSynonymsToMap("pneumonoultramicroscopicsilicavolcanoconiosis.", new HashMap<>());
            fail();
        } catch (RuntimeException e) {
            assertSame(NoSuchFileException.class, e.getCause().getClass());
            assertEquals("DataManager.readFile() failed. Path: pneumonoultramicroscopicsilicavolcanoconiosis.",
                    e.getMessage());
        }
    }

    // Testing a special case for an internal method, since all public methods and other cases are implicitly used
    // and verified in other files' tests.
    @Test
    void testInvalidMakeFile() {
        try {
            DataManager.makeFile("enneacontaka::eneagon..");
            fail();
        } catch (RuntimeException e) {
            assertEquals("DataManager.makeFile() failed. Path: enneacontaka::eneagon..", e.getMessage());
        }
    }

    // Testing a special case for an internal method, since all public methods and other cases are implicitly used
    // and verified in other files' tests.
    @Test
    void testInvalidWriteFile() {
        try {
            DataManager.writeObject("Object", "enneacontaka::eneagon..");
            fail();
        } catch (RuntimeException e) {
            assertEquals("DataManager.writeFile() failed. Path: enneacontaka::eneagon..", e.getMessage());
        }
    }
}
