package model.player;

import exceptions.InvalidActionException;
import model.StoryController;

import java.util.*;

public class Interpreter {

    private StoryController story;
    private static final Map<String, String> ACTIONS = new HashMap<>();

    public Interpreter(StoryController story) {
        this.story = story;
        initializeActions();
    }

    public void initializeActions() {
        ACTIONS.put("view", "view");
        ACTIONS.put("check", "view");
        ACTIONS.put("observe", "view");
        ACTIONS.put("analyze", "view");
        ACTIONS.put("analyse", "view");
        ACTIONS.put("look at", "view");
        ACTIONS.put("stare at", "view");
        ACTIONS.put("search", "search");
        ACTIONS.put("investigate", "search");
        ACTIONS.put("go to", "goto");
        ACTIONS.put("move to", "goto");
        ACTIONS.put("walk to", "goto");
    }

    // EFFECTS: gets and returns the processed input that the user types
    public String userInput(String input) throws InvalidActionException {
        input = removeLeadingPronoun(removeLeadingSpaces(removeTrailingSpaces(input.toLowerCase())));
        String[] actionWords = processAction(input.split(" "));

        return actionWords[0] + "@" + actionWords[1];
    }

    // REQUIRES: non-empty array of keywords constructed from user input, split by spaces
    // EFFECTS: configures set of input words to contain a valid action in position 0 and object in position 1,
    //          or throws InvalidActionException if cannot
    private String[] processAction(String[] keywords) throws InvalidActionException {
        List<String> actionWords = new ArrayList<>(Arrays.asList(keywords));
        try {
            actionWords.set(0, ACTIONS.get(keywords[0]));
        } catch (NullPointerException n1) {
            try {
                actionWords.set(0, ACTIONS.get(keywords[0] + " " + keywords[1]));
                actionWords.remove(1);
            } catch (NullPointerException n2) {
                try {
                    actionWords.set(0, ACTIONS.get(keywords[0] + " " + keywords[1] + " " + keywords[2]));
                    actionWords.remove(1);
                    actionWords.remove(2);
                } catch (NullPointerException n3) {
                    throw new InvalidActionException();
                }
            }
        }
        if (actionWords.get(1).equals("a") || actionWords.get(1).equals("the")) {
            actionWords.remove(1);
        }
        return actionWords.toArray(new String[] {});
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
