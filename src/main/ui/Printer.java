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
                getUserActivity();
                break;
        }
    }

    // EFFECTS: program is paused until user inputs anything.
    public void continueText() {
        System.out.print("[Enter to continue]");
        scanner.nextLine();
    }

    private boolean getUserActivity() {
        try {
            String[] keywords = interpreter.userInput(scanner.nextLine());
        } catch (InvalidActionException e) {
            return false;
        }

        // TODO: construct actionCode, check if it's valid, call StoryController.executeAction()

        return true;
    }
}
