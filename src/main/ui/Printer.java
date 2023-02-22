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

    public void printScene() {
        isPrinting = true;
        while (isPrinting) {
            try {
                System.out.println(story.getCurrentNextLine());
                continueText();
            } catch (SceneEndingException e) {
                isPrinting = false;
                String msg = e.getLastMessage();
                if (msg != null) {
                    System.out.println(msg);
                }
                handleEndSceneEvents(e.getEvents());
            }
        }
    }

    // EFFECTS: triggers all corresponding end scene events.
    private void handleEndSceneEvents(List<EndSceneEvent> endEvents) {
        boolean containsExplore = false;
        EndSceneEvent nextSceneEvent = null;
        for (EndSceneEvent event : endEvents) {
            switch (event.getType()) {
                case EXPLORE:
                    containsExplore = true;
                    break;
                case NEXT_SCENE:
                    nextSceneEvent = event;
                    break;
                case LEARN_SPELL:
                    break;
                case ACQUIRE_ITEM:
                    break;
            }
        }

        finishScene(containsExplore, nextSceneEvent);
    }

    // EFFECTS: determines what will happen after this scene.
    private void finishScene(boolean explore, EndSceneEvent nextSceneEvent) {
        if (explore) {
            isExploring = true;
        } else if (nextSceneEvent != null) {
            try {
                story.setCurrentScene(nextSceneEvent.getKeyword());
            } catch (InvalidSceneException no) {
                System.err.println("Incorrect NextScene call:");
                no.printStackTrace(System.err);
            }
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
