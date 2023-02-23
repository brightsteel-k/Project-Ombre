package ui;

import exceptions.InvalidActionException;
import exceptions.SceneEndingException;
import model.StoryController;
import model.Interpreter;
import model.scenes.SceneEvent;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Controls text and interface with which the user interacts
public class Printer {

    private final StoryController story;
    private final Interpreter interpreter;
    private static final Scanner SCANNER = new Scanner(System.in);
    private boolean isPrinting = false;
    private boolean isExploring = false;
    private final Random random;

    public Printer() {
        story = new StoryController();
        story.setCurrentScene("test");
        story.setCurrentLocation("front");
        interpreter = new Interpreter(story);
        random = new Random();
        game();
    }

    public void game() {
        while (true) {
            printScene();

            if (isExploring) {
                handleExploring();
            }
        }
    }

    // EFFECTS: prints the successive lines in an exposition scene, one by one, separated by a call to continueText()
    public void printScene() {
        isPrinting = true;
        while (isPrinting) {
            try {
                System.out.println(story.getCurrentNextLine());
                continueText();
            } catch (SceneEndingException e) {
                isPrinting = false;
                handleSceneEvents(e.getEvents());
                finishScene(e.shouldStartExploring(), e.getNextScene());
            }
        }
    }

    // EFFECTS: triggers all given scene events
    private void handleSceneEvents(List<SceneEvent> sceneEvents) {
        for (SceneEvent event : sceneEvents) {
            handleSceneEvent(event);
        }
    }

    // EFFECTS: executes given scene event, with varying effects depending on its type and supplied keyword
    private void handleSceneEvent(SceneEvent event) {
        switch (event.getType()) {
            case DISPLAY_TEXT:
                System.out.println(event.getKeyword());
                break;
            case CHANGE_LOCATION:
                changeLocation(event.getKeyword());
                break;
            case LEARN_SPELL:
                break;
            case ACQUIRE_ITEM:
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: determines what will happen after a scene, based on the parameters taken from its ending exception
    private void finishScene(boolean explore, String nextSceneId) {
        if (explore) {
            isExploring = true;
        } else if (nextSceneId != null) {
            story.setCurrentScene(nextSceneId);
            isExploring = false;
        }
    }

    // EFFECTS: pauses program until player inputs something
    public void continueText() {
        System.out.print("[Enter to continue]");
        SCANNER.nextLine();
    }

    // EFFECTS: keeps querying player for input actions and executing corresponding effects, as long as isExploring
    //          is true.
    private void handleExploring() {
        while (isExploring) {
            try {
                executeUserInput();
            } catch (InvalidActionException e) {
                printInvalidAction(e);
            }
        }
    }

    // MODIFIES: story
    // EFFECTS: receives user input from Interpreter and carries the action out with the StoryController, if valid
    private void executeUserInput() throws InvalidActionException {
        String[] actionWords = interpreter.userInput(SCANNER.nextLine());

        System.out.println("ActionCode: " + actionWords[0]); // TODO: REMOVE DEBUGGING PRINT
        handleSceneEvents(story.executeAction(actionWords));
    }

    // MODIFIES: story
    // EFFECTS: updates player's location, prints corresponding message
    private void changeLocation(String newLocation) {
        System.out.println(story.changeLocation(newLocation));
    }

    // EFFECTS: prints message telling the player their previous action was invalid
    private void printInvalidAction(InvalidActionException e) {
        if (e.invalidObject()) {
            if (random.nextBoolean()) {
                System.out.println("You cannot reach a " + e.getInvalidObject() + " from where you are.");
            } else {
                System.out.println("There is no " + e.getInvalidObject() + " where you are.");
            }
        } else {
            System.out.println("You cannot do that.");
        }
    }
}
