package ui;

import model.StoryController;

import java.util.Scanner;

// Controls text and interface with which the user interacts
public class Printer {

    private static final Scanner scanner = new Scanner(System.in);

    public Printer() {
        
    }

    public static void printText(String output) {
        System.out.println(output);
    }

    public static void continueText() {
        System.out.print("[Enter to continue]");
        scanner.nextLine();
        StoryController.printNextLine();
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
