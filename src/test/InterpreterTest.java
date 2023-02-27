import exceptions.AmbiguousActionException;
import exceptions.InvalidActionException;
import model.Interpreter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Deserializer;
import util.Operations;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterTest {

    private Interpreter testInterpreter;

    @BeforeAll
    static void init() {
        Deserializer.initializeGson();
    }

    @BeforeEach
    void setup() {
        testInterpreter = new Interpreter();
    }

    @Test
    void testConstructorSynonyms() {
        try {
            assertOutput("view", testInterpreter.userInput("observe"));
            assertOutput("view", testInterpreter.userInput("analyze"));
            assertOutput("view", testInterpreter.userInput("look at"));
            assertOutput("goto", testInterpreter.userInput("move to"));
            assertOutput("search", testInterpreter.userInput("investigate"));
        } catch (InvalidActionException e) {
            fail();
        }
    }

    @Test
    void testUserInputLeadingSpace() {
        try {
            assertOutput("view", testInterpreter.userInput(" view"));
            assertOutput("search", testInterpreter.userInput("     SEARCH"));
        } catch (InvalidActionException e) {
            fail();
        }
    }

    @Test
    void testUserInputTrailingSpace() {
        try {
            assertOutput("view", testInterpreter.userInput("VIEW "));
            assertOutput("take", testInterpreter.userInput("take       "));
        } catch (InvalidActionException e) {
            fail();
        }
    }

    @Test
    void testUserInputLeadingPronouns() {
        try {
            assertOutput("view", testInterpreter.userInput("i view"));
            assertOutput("search", testInterpreter.userInput("I will search"));
            assertOutput("take", testInterpreter.userInput("I would like to TAKE"));
        } catch (InvalidActionException e) {
            fail();
        }
    }

    @Test
    void testUserInputInvalid() {
        assertInvalidAction(testInterpreter, "hi@mail.com");
        assertInvalidAction(testInterpreter, "");
        assertInvalidAction(testInterpreter, "    ");
        assertInvalidAction(testInterpreter, "prevaricate");
        assertInvalidAction(testInterpreter, "prevaricate profusely");
        assertInvalidAction(testInterpreter, "prevaricate profusely, refuse");
        assertInvalidAction(testInterpreter, "prevaricate profusely, refuse all");
        assertInvalidAction(testInterpreter, "prevaricate profusely, refuse cooperation");
        assertInvalidAction(testInterpreter, "take and view");
    }

    @Test
    void testUserInputDeterminers() {
        try {
            assertOutput("view", "object", testInterpreter.userInput("view the object"));
            assertOutput("view", "object", testInterpreter.userInput("view an object"));
            assertOutput("take", "object", testInterpreter.userInput("take every object"));
            assertOutput("take", "object", testInterpreter.userInput("take that object"));
            assertOutput("goto", "places", testInterpreter.userInput("go to those places"));
            assertOutput("goto", "places", testInterpreter.userInput("I will go to all places"));
        } catch (InvalidActionException e) {
            fail();
        }
    }

    @Test
    void testUserInputPronouns() {
        assertInvalidAction(AmbiguousActionException.class, testInterpreter, "view it");
        assertInvalidAction(AmbiguousActionException.class, testInterpreter, "go to him");
        assertInvalidAction(AmbiguousActionException.class, testInterpreter, "stare at that");
    }

    private void assertInvalidAction(Interpreter interpreter, String input) {
        assertInvalidAction(InvalidActionException.class, interpreter, input);
    }

    private void assertInvalidAction(Class expectedType, Interpreter interpreter, String input) {
        try {
            interpreter.userInput(input);
            fail();
        } catch (InvalidActionException e) {
            if (e.getClass() != expectedType) {
                fail();
            }
        }
    }

    private void assertOutput(String expected0, String[] actual) {
        assertTrue(Operations.arraysEqual(new String[] { expected0 }, actual));
    }

    private void assertOutput(String expected0, String expected1, String[] actual) {
        assertTrue(Operations.arraysEqual(new String[] { expected0, expected1 }, actual));
    }
}
