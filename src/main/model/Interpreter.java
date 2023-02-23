package model;

import exceptions.InvalidActionException;
import model.player.StoryController;

import java.util.*;

// Contains logic to process and partially parse player input into usable set of action words
public class Interpreter {

    private final StoryController story;
    private static final Map<String, String> ACTION_SYNONYMS = new HashMap<>();

    public Interpreter(StoryController story) {
        this.story = story;
        initializeActions();
    }

    // MODIFIES: this
    // EFFECTS: Relates synonyms to their corresponding actions in the game in one giant map.
    //          Eventually will read data from serialized json files instead.
    public void initializeActions() {
        ACTION_SYNONYMS.put("view", "view");
        ACTION_SYNONYMS.put("check", "view");
        ACTION_SYNONYMS.put("observe", "view");
        ACTION_SYNONYMS.put("analyze", "view");
        ACTION_SYNONYMS.put("analyse", "view");
        ACTION_SYNONYMS.put("look at", "view");
        ACTION_SYNONYMS.put("stare at", "view");
        ACTION_SYNONYMS.put("search", "search");
        ACTION_SYNONYMS.put("investigate", "search");
        ACTION_SYNONYMS.put("go to", "goto");
        ACTION_SYNONYMS.put("move to", "goto");
        ACTION_SYNONYMS.put("walk to", "goto");
    }

    // EFFECTS: gets and returns the processed input that the user types
    public String[] userInput(String input) throws InvalidActionException {
        input = removeLeadingPronoun(removeLeadingSpaces(removeTrailingSpaces(input.toLowerCase())));
        if (input.contains("@")) {
            throw new InvalidActionException();
        }
        return processAction(input.split(" "));
    }

    // REQUIRES: keywords is a non-empty array of words without spaces or "@"
    // EFFECTS: configures set of input words to contain a valid action in position 0 and optionally an object
    //          in position 1, or throws exception if unable.
    private String[] processAction(String[] keywords) throws InvalidActionException {
        List<String> actionWords = new ArrayList<>(Arrays.asList(keywords));
        String action = null;
        int keyNum = 0;

        while (action == null) {
            keyNum++;
            if (keyNum == 4) {
                throw new InvalidActionException();
            }
            action = tryParseAction(actionWords, keyNum);
        }

        actionWords.subList(0, keyNum).clear();
        actionWords.add(0, action);

        return configureWordsList(actionWords);
    }

    // REQUIRES: relevantWords > 0
    // EFFECTS: tries to put together a keyword from the given number of relevant words in the given list, throws an
    //          exception if unable. Otherwise, translates keyword to an action code and returns result.
    private String tryParseAction(List<String> actionWords, int relevantWords) throws InvalidActionException {
        if (actionWords.size() < relevantWords) {
            throw new InvalidActionException();
        }

        String possibleKeyword = "";
        for (int n = 0; n < relevantWords; n++) {
            possibleKeyword += actionWords.get(n);
            if (n < relevantWords - 1) {
                possibleKeyword += " ";
            }
        }
        return ACTION_SYNONYMS.get(possibleKeyword);
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

    // EFFECTS: if given string begins with "i ", "i would like to ", or "i will ", get rid of that prefix
    //          and return the rest; else, return the given string.
    private String removeLeadingPronoun(String input) {
        if (input.startsWith("i would like to ")) {
            return input.substring(16);
        }
        if (input.startsWith("i will ")) {
            return input.substring(7);
        }
        if (input.startsWith("i ")) {
            return input.substring(2);
        }
        return input;
    }
}
