package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class DeserializerTest {

    @BeforeEach
    void setup() {
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
        }
    }
}
