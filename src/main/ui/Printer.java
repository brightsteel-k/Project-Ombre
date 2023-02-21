package ui;

import exceptions.InvalidActionException;
import exceptions.NoNextSceneException;
import exceptions.SceneEndingException;
import model.StoryController;
import model.player.Interpreter;
import model.scenes.EndSceneEvent;

import java.util.Scanner;

// Controls text and interface with which the user interacts
public class Printer {

    private StoryController story;
    private Interpreter interpreter;
    private static final Scanner scanner = new Scanner(System.in);
    private boolean isPrinting = false;

    public Printer() {
        story = new StoryController();
        story.setCurrentScene("intro");
        printScene();
    }

    public void printScene() {
        isPrinting = true;
        while (isPrinting) {
            try {
                System.out.println(story.getCurrentNextLine());
                continueText();
            } catch (SceneEndingException e) {
                handleEndScene(e.getEvent());
                isPrinting = false;
            }
        }
    }

    private void handleEndScene(EndSceneEvent e) {
        switch (e) {
            case NEXT_SCENE:
                try {
                    story.switchToNextScene();
                } catch (NoNextSceneException no) {
                    System.err.println("Incorrect NextScene call:");
                    no.printStackTrace(System.err);
                }
                break;
            case FREE_ROAM:
                handleFreeRoam();
                break;
        }
    }

    // EFFECTS: pauses program until player inputs something
    public void continueText() {
        System.out.print("[Enter to continue]");
        scanner.nextLine();
    }


    private void handleFreeRoam() {
        try {
            executeUserInput();
        } catch (InvalidActionException e) {
            // TODO: handle invalid actions
        }
    }

    // EFFECTS: receives user input from Interpreter and carries the action out with the StoryController, if valid
    private void executeUserInput() throws InvalidActionException {
        String actionCode = interpreter.userInput(scanner.nextLine());

        if (actionCode.startsWith("goto")) {
            story.changeLocation(actionCode.substring(5));
            printLocation();
        }

        story.executeAction(actionCode);
    }

    // EFFECTS: prints update to player's location
    private void printLocation() {
        System.out.println("You make your way to " + story.getCurrentLocation().getName() + ".");
    }
}
