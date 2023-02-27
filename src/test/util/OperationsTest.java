package util;

import model.storyobjects.Scene;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OperationsTest {

    @Test
    void testConstructor() {
        Operations operations = new Operations();
    }

    @Test
    void testArraysEqual() {
        String[] words1 = new String[] { "apple", "orange", "banana" };
        String[] words2 = new String[] { "apple", "windows", "android" };
        String[] words3 = new String[] { "apple", "orange", "lemon", "lime" };
        Integer[] words4 = new Integer[] { 4, 8, 12, 16 };
        assertTrue(Operations.arraysEqual(words1, words1));
        assertFalse(Operations.arraysEqual(words1, words2));
        assertFalse(Operations.arraysEqual(words1, words3));
        assertFalse(Operations.arraysEqual(words3, words4));
    }

    @Test
    void testObjectsEqual() {
        assertTrue(Operations.objectsEqual("Hello", "Hello"));
        assertFalse(Operations.objectsEqual("Tanzanite", new Scene()));
        assertFalse(Operations.objectsEqual(null, "Sapphire"));
        assertTrue(Operations.objectsEqual(null, null));
    }
}
