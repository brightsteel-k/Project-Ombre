package model;

import exceptions.AmbiguousActionException;
import exceptions.InvalidActionException;
import util.Deserializer;

import java.util.*;

// Contains logic to process and partially parse player input into usable set of action words
public class Interpreter {

    private static final Map<String, String> ACTION_SYNONYMS = new HashMap<>();
    private static final List<String> DETERMINERS = new ArrayList<>();
    private static final List<String> VAGUE_PRONOUNS = new ArrayList<>();

    // MODIFIES: this, Interpreter
    // EFFECTS: maps action keywords to valid synonyms, loads blacklist of unwanted input words to facilitate
    //          the parsing process.
    public Interpreter() {
        Deserializer.loadSynonymsToMap("data/synonyms/actions.json", ACTION_SYNONYMS);
        if (DETERMINERS.size() == 0) {
            initializeBlacklistWords();
        }
    }

    // MODIFIES: Interpreter
    // EFFECTS: populates master maps of determiners and vague pronouns that should be deleted from user input
    private void initializeBlacklistWords() {
        DETERMINERS.add("a");
        DETERMINERS.add("an");
        DETERMINERS.add("the");
        DETERMINERS.add("some");
        DETERMINERS.add("every");
        DETERMINERS.add("all");
        DETERMINERS.add("of");
        DETERMINERS.add("this");
        DETERMINERS.add("that");
        DETERMINERS.add("those");
        VAGUE_PRONOUNS.add("them");
        VAGUE_PRONOUNS.add("it");
        VAGUE_PRONOUNS.add("him");
        VAGUE_PRONOUNS.add("her");
    }

    // EFFECTS: gets and returns the processed input that the user types
    public String[] userInput(String input) throws InvalidActionException {
        input = removeLeadingPronoun(removeLeadingSpaces(removeTrailingSpaces(input.toLowerCase())));
        if (input.contains("@")) {
            throw new InvalidActionException();
        }
        return processAction(input.split(" "));
    }

    // REQUIRES: keywords is an array of words without spaces or "@"
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

        StringBuilder possibleKeyword = new StringBuilder();
        for (int n = 0; n < relevantWords; n++) {
            possibleKeyword.append(actionWords.get(n));
            if (n < relevantWords - 1) {
                possibleKeyword.append(" ");
            }
        }
        return ACTION_SYNONYMS.get(possibleKeyword.toString());
    }

    // MODIFIES: actionWords
    // EFFECTS: removes determiners from 2nd position, throws exception if input contains "and" or operates on
    //          ambiguous pronouns, otherwise returns given list as an array of length 2
    private String[] configureWordsList(List<String> actionWords) throws InvalidActionException {
        if (actionWords.contains("and")) {
            throw new InvalidActionException();
        }

        if (actionWords.size() > 1) {
            String det = actionWords.get(1);
            while (DETERMINERS.contains(det)) {
                actionWords.remove(1);
                if (actionWords.size() == 1) {
                    throw new AmbiguousActionException();
                } else {
                    det = actionWords.get(1);
                }
            }
            if (VAGUE_PRONOUNS.contains(actionWords.get(1))) {
                throw new AmbiguousActionException();
            }
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
        String[] prefixes = new String[] { "i would like to ", "i will ", "i " };
        for (String prefix : prefixes) {
            if (input.startsWith(prefix)) {
                return input.substring(prefix.length());
            }
        }
        return input;
    }
}
