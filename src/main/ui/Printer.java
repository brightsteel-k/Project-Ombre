package ui;

import java.util.Scanner;

public class Printer {

    Scanner scanner = new Scanner(System.in);

    public Printer() {

    }


    // EFFECTS: gets and returns the processed input that the user types
    public String userInput() {
        String response = scanner.nextLine();

        response = removeLeadingPronoun(removeLeadingSpaces(removeTrailingSpaces(response.toLowerCase())));

        return response;
    }

    // EFFECTS: returns given string with all leading spaces removed
    private String removeLeadingSpaces(String input) {
        if (input.substring(0, 1).equals(" ")) {
            return removeLeadingSpaces(input.substring(1));
        }

        return input;
    }

    // EFFECTS: returns given string with all trailing spaces removed
    private String removeTrailingSpaces(String input) {
        if (input.substring(input.length() - 1).equals(" ")) {
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
