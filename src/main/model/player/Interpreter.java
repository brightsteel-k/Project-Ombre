package model.player;

import exceptions.InvalidActionException;
import model.StoryController;

import java.util.HashMap;
import java.util.Map;

public class Interpreter {

    private StoryController story;
    private static final Map<String, String> ACTIONS = new HashMap<>();

    public Interpreter(StoryController story) {
        this.story = story;
        initalizeActions();
    }

    public void initalizeActions() {
        ACTIONS.put("view", "view");
        ACTIONS.put("check", "view");
        ACTIONS.put("observe", "view");
        ACTIONS.put("analyze", "view");
        ACTIONS.put("analyse", "view");
        ACTIONS.put("look at", "view");
        ACTIONS.put("stare at", "view");
        ACTIONS.put("search", "search");
        ACTIONS.put("investigate", "search");
    }

    // EFFECTS: gets and returns the processed input that the user types
    public String[] userInput(String input) throws InvalidActionException {
        input = removeLeadingPronoun(removeLeadingSpaces(removeTrailingSpaces(input.toLowerCase())));
        String[] keywords = input.split(" ");

        keywords[0] = processAction(keywords);

        return keywords;
    }

    private String processAction(String[] keywords) throws InvalidActionException {
        String action;
        try {
            action = ACTIONS.get(keywords[0]);
        } catch (NullPointerException n0) {
            try {
                action = ACTIONS.get(keywords[0] + " " + keywords[1]);
            } catch (NullPointerException n1) {
                throw new InvalidActionException();
            }
        }

        return action;
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
