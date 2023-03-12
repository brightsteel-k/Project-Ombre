package util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class DeserializerTest {

    @BeforeAll
    static void setup() {
        Deserializer.initializeGson();
    }

    @Test
    void testConstructor() {
        Deserializer deserializer = new Deserializer();
    }

    // Testing a special case for an internal method, since all public methods and other cases are implicitly used
    // and verified in other files' tests.
    @Test
    void testInvalidReadFile() {
        try {
            Deserializer.loadSynonymsToMap("pneumonoultramicroscopicsilicavolcanoconiosis.", new HashMap<>());
            fail();
        } catch (RuntimeException e) {
            assertSame(NoSuchFileException.class, e.getCause().getClass());
            assertEquals("Deserializer.readFile() failed. Path: pneumonoultramicroscopicsilicavolcanoconiosis.",
                    e.getMessage());
        }
    }

    // Testing a special case for an internal method, since all public methods and other cases are implicitly used
    // and verified in other files' tests.
    @Test
    void testInvalidMakeFile() {
        try {
            Deserializer.makeFile("enneacontaka::eneagon..");
            fail();
        } catch (RuntimeException e) {
            assertEquals("Deserializer.makeFile() failed. Path: enneacontaka::eneagon..", e.getMessage());
        }
    }

    // Testing a special case for an internal method, since all public methods and other cases are implicitly used
    // and verified in other files' tests.
    @Test
    void testInvalidWriteFile() {
        try {
            Deserializer.writeObject("Object", "enneacontaka::eneagon..");
            fail();
        } catch (RuntimeException e) {
            assertEquals("Deserializer.writeFile() failed. Path: enneacontaka::eneagon..", e.getMessage());
        }
    }
}
