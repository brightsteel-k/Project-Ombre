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

    // MODIFIES: this
    // EFFECTS: Relates synonyms to their corresponding actions in the game in one giant map.
    //          Eventually will read data from serialized json files instead.
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
    public String[] userInput(String input) throws InvalidActionException {
        input = removeLeadingPronoun(removeLeadingSpaces(removeTrailingSpaces(input.toLowerCase())));
        if (input.contains("@")) {
            throw new InvalidActionException();
        }
        return processAction(input.split(" "));
    }

    // REQUIRES: non-empty array of keywords constructed from user input, split by spaces
    // EFFECTS: configures set of input words to contain a valid action in position 0 and object in position 1,
    //          or throws InvalidActionException if cannot
    private String[] processAction(String[] keywords) throws InvalidActionException {
        List<String> actionWords = new ArrayList<>(Arrays.asList(keywords));
        String action = null;
        int keyNum = 0;

        checkMinActionSize(actionWords, 1);
        action = ACTIONS.get(keywords[0]);
        if (action == null) {
            checkMinActionSize(actionWords, 2);
            action = ACTIONS.get(keywords[0] + " " + keywords[1]);
            keyNum = 1;
        }
        if (action == null) {
            checkMinActionSize(actionWords, 3);
            action = ACTIONS.get(keywords[0] + " " + keywords[1] + " " + keywords[2]);
            keyNum = 2;
        }
        if (action == null) {
            throw new InvalidActionException();
        }

        for (int k = 0; k < keyNum; k++) {
            actionWords.remove(k);
        }
        actionWords.add(0, action);
        return configureWordsList(actionWords);
    }

    // EFFECTS: throws exception iff given list is smaller than given minimum size
    private void checkMinActionSize(List<String> actionWords, int minSize) throws InvalidActionException {
        if (actionWords.size() < minSize) {
            throw new InvalidActionException();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes determiners from 2nd position, return list as an array
    private String[] configureWordsList(List<String> actionWords) {
        if (actionWords.size() > 1 && (actionWords.get(1).equals("a") || actionWords.get(1).equals("the"))) {
            actionWords.remove(1);
        }

        return actionWords.subList(0, Math.min(actionWords.size(), 2)).toArray(new String[]{});
    }

    // EFFECTS: returns given string with all leading spaces removed
    private String removeLeadingSpaces(String input) {
        if (input.startsWith(" ")) {
            return removeLeadingSpaces(input.substring(1));
        }
        return input;
    }

    // EFFECTS: returns given string with all trailing spaces removed
    private String removeTrailingSpaces(String input) {
        if (input.endsWith(" ")) {
            return removeTrailingSpaces(input.substring(0, input.length() - 1));
        }
        return input;
    }

    // EFFECTS: if given string begins with "i ", get rid of pronoun and return the rest;
    //          else, return the given string
    private String removeLeadingPronoun(String input) {
        if (input.startsWith("i ")) {
            return input.substring(2);
        }

        if (input.startsWith("would like to ")) {
            return input.substring(14);
        } else if (input.startsWith("will ")) {
            return input.substring(5);
        }
        return input;
    }
}
