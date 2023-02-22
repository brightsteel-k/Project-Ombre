package ui;

import exceptions.InvalidActionException;
import exceptions.InvalidSceneException;
import exceptions.SceneEndingException;
import model.StoryController;
import model.player.Interpreter;
import model.scenes.EndSceneEvent;

import java.util.List;
import java.util.Scanner;

// Controls text and interface with which the user interacts
public class Printer {

    private final StoryController story;
    private final Interpreter interpreter;
    private static final Scanner SCANNER = new Scanner(System.in);
    private boolean isPrinting = false;
    private boolean isExploring = false;

    public Printer() {
        story = new StoryController();
        story.setCurrentScene("test");
        interpreter = new Interpreter(story);
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

    // EFFECTS: prints the successive lines in an exposition scene, one by one, separated by a call to continueText().
    public void printScene() {
        isPrinting = true;
        while (isPrinting) {
            try {
                System.out.println(story.getCurrentNextLine());
                continueText();
            } catch (SceneEndingException e) {
                isPrinting = false;
                handleEndSceneEvents(e.getEvents());
            }
        }
    }

    // EFFECTS: triggers all corresponding end scene events.
    private void handleEndSceneEvents(List<EndSceneEvent> endEvents) {
        boolean containsExplore = false;
        String nextSceneId = null;
        for (EndSceneEvent event : endEvents) {
            switch (event.getType()) {
                case START_EXPLORING:
                    containsExplore = true;
                    break;
                case DISPLAY_TEXT:
                    System.out.println(event.getKeyword());
                    break;
                case NEXT_SCENE:
                    nextSceneId = event.getKeyword();
                    break;
                case LEARN_SPELL:
                    break;
                case ACQUIRE_ITEM:
                    break;
            }
        }

        finishScene(containsExplore, nextSceneId);
    }

    // EFFECTS: determines what will happen after this scene.
    private void finishScene(boolean explore, String nextSceneId) {
        if (explore) {
            isExploring = true;
        } else if (nextSceneId != null) {
            story.setCurrentScene(nextSceneId);
            isExploring = false;
        }
    }

    // EFFECTS: pauses program until player inputs something.
    public void continueText() {
        System.out.print("[Enter to continue]");
        SCANNER.nextLine();
    }

    // EFFECTS:
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
    // EFFECTS: receives user input from Interpreter and carries the action out with the StoryController, if valid.
    private void executeUserInput() throws InvalidActionException {
        String[] actionWords = interpreter.userInput(SCANNER.nextLine());

        System.out.println("ActionCode: " + actionWords[0]); // TODO: REMOVE DEBUGGING PRINT

        if (actionWords[0].equals("goto")) {
            changeLocation(actionWords[1]);
        } else {
            story.executeAction(actionWords);
        }

    }

    // MODIFIES: story
    // EFFECTS: updates player's location, prints corresponding message.
    private void changeLocation(String newLocation) throws InvalidActionException {
        System.out.println(story.changeLocation(newLocation));
    }

    // EFFECTS: prints message telling the player their previous action was invalid.
    private void printInvalidAction(InvalidActionException e) {
        if (e.invalidObject()) {
            System.out.println("You cannot reach a " + e.getInvalidObject() + " from where you are.");
        } else {
            System.out.println("You cannot do that.");
        }
    }
}
