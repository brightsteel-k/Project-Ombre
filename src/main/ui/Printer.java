package ui;

import exceptions.NoNextSceneException;
import exceptions.SceneEndingException;
import model.StoryController;
import model.scenes.EndSceneEvent;

import java.util.Scanner;

// Controls text and interface with which the user interacts
public class Printer {

    private static StoryController STORY;
    private static final Scanner scanner = new Scanner(System.in);
    private boolean isPrinting = false;

    public Printer() {
        STORY = new StoryController();
        STORY.setCurrentScene("intro");
        printScene();
    }

    public void printScene() {
        isPrinting = true;
        while (isPrinting) {
            try {
                System.out.println(STORY.getCurrentNextLine());
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
                    STORY.switchToNextScene();
                } catch (NoNextSceneException no) {
                    System.err.println("Incorrect NextScene call:");
                    no.printStackTrace(System.err);
                }
                break;
            case FREE_ROAM:
                System.out.println("Explore"); // TODO: Implement free roam
                break;
        }
    }

    public void continueText() {
        System.out.print("[Enter to continue]");
        scanner.nextLine();
    }

    // EFFECTS: gets and returns the processed input that the user types
    public String userInput() {
        String response = scanner.nextLine();
        response = removeLeadingPronoun(removeLeadingSpaces(removeTrailingSpaces(response.toLowerCase())));
        return response;
    }

    // EFFECTS: returns given string with all leading spaces removed
    private String removeLeadingSpaces(String input) {
        if (input.charAt(0) == ' ') {
            return removeLeadingSpaces(input.substring(1));
        }
        return input;
    }

    // EFFECTS: returns given string with all trailing spaces removed
    private String removeTrailingSpaces(String input) {
        if (input.charAt(input.length() - 1) == ' ') {
            return removeTrailingSpaces(input.substring(0, input.length() - 1));
        }
        return input;
    }

    // EFFECTS: if given string begins with "i ", get rid of pronoun and return the rest;
    //          else, return the given string
    private String removeLeadingPronoun(String input) {
        if (input.substring(0, 2).equals("i ")) {
            return input.substring(2);
        }
        return input;
    }
}
