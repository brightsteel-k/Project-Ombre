package ui;

import exceptions.InvalidActionException;
import exceptions.SceneEndingException;
import model.player.Player;
import model.player.StoryController;
import model.Interpreter;
import model.scenes.SceneEvent;
import util.Deserializer;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Controls text and interface with which the user interacts, runs the model code together
public class Printer {

    private final Player player;
    private final StoryController story;
    private final Interpreter interpreter;
    private static final Scanner SCANNER = new Scanner(System.in);
    private boolean isPrinting = false;
    private boolean isExploring = false;
    private final Random random;

    // EFFECTS: Printer has its own deserializer, random number generator, interpreter, and initialized story objects
    //          that will work together to present the user with a text-based adventure experience.
    public Printer() {
        Deserializer.initializeGson();
        random = new Random();
        player = new Player();
        story = new StoryController(player);
        interpreter = new Interpreter();
        story.setCurrentScene("test");
        story.setCurrentLocation("front");
        game();
    }

    // MODIFIES: this, this object's story instance, this object's player instance
    // EFFECTS: starts the gameplay loop
    public void game() {
        while (true) {
            printScene();

            if (isExploring) {
                handleExploring();
            }
        }
    }

    // MODIFIES: this, this object's story instance, this object's player instance
    // EFFECTS: prints the successive lines in an exposition scene, one by one, separated by a call to continueText()
    public void printScene() {
        isPrinting = true;
        while (isPrinting) {
            try {
                System.out.println(story.getCurrentNextLine());
                continueText();
            } catch (SceneEndingException e) {
                isPrinting = false;
                handleSceneEvents(e.getEvents());
                finishScene(e.shouldStartExploring(), e.getNextScene());
            }
        }
    }

    // MODIFIES: this object's story instance, this object's player instance
    // EFFECTS: triggers all given scene events
    private void handleSceneEvents(List<SceneEvent> sceneEvents) {
        for (SceneEvent event : sceneEvents) {
            handleSceneEvent(event);
        }
    }

    // MODIFIES: this object's story instance, this object's player instance
    // EFFECTS: executes given scene event, with varying effects depending on its type and supplied keyword
    private void handleSceneEvent(SceneEvent event) {
        if (event.hasCondition() && !story.conditionFulfilled(event.getCondition())) {
            return;
        }
        switch (event.getType()) {
            case DISPLAY_TEXT:
                System.out.println(event.getKeyword());
                break;
            case CHANGE_LOCATION:
                changeLocation(event.getKeyword());
                break;
            case LEARN_SPELL:
                player.addSpell(event.getKeyword());
                break;
            case ACQUIRE_ITEM:
                player.addItem(event.getKeyword());
                break;
            case SET_CONDITION:
                String[] info = event.getKeyword().split(":");
                player.setCondition(info[0], info[1]);
                break;
        }
    }

    // MODIFIES: this, this object's story instance
    // EFFECTS: determines what will happen after a scene, based on the parameters taken from its ending exception
    private void finishScene(boolean explore, String nextSceneId) {
        if (explore) {
            isExploring = true;
        } else if (nextSceneId != null) {
            story.setCurrentScene(nextSceneId);
            isExploring = false;
        }
    }

    // EFFECTS: pauses program until player inputs something
    public void continueText() {
        System.out.print("[Enter to continue]");
        SCANNER.nextLine();
    }

    // MODIFIES: this object's story instance, this object's player instance
    // EFFECTS: keeps querying player for input actions and executing corresponding effects, as long as player is
    //          exploring.
    private void handleExploring() {
        while (isExploring) {
            try {
                executeUserInput();
            } catch (InvalidActionException e) {
                printInvalidAction(e);
            }
        }
    }

    // MODIFIES: this object's story instance, this object's player instance
    // EFFECTS: receives user input, passes it to the story object for formatting into an action code,
    //          carries the action out the action if valid. Else, throws exception.
    private void executeUserInput() throws InvalidActionException {
        String[] actionWords = interpreter.userInput(SCANNER.nextLine());

        System.out.println("ActionCode: " + actionWords[0]); // TODO: REMOVE DEBUGGING PRINT
        handleSceneEvents(story.executeAction(actionWords));
    }

    // MODIFIES: this object's story instance
    // EFFECTS: updates player's location in the story, prints corresponding message
    private void changeLocation(String newLocation) {
        System.out.println(story.changeLocation(newLocation));
    }

    // EFFECTS: prints message telling the player their previous action was invalid
    private void printInvalidAction(InvalidActionException e) {
        switch (e.getType()) {
            case 1:
                if (random.nextBoolean()) {
                    System.out.println("You cannot reach any " + e.getInvalidObject() + " from where you are.");
                } else {
                    System.out.println("There is no " + e.getInvalidObject() + " where you are.");
                }
                break;
            case 2:
                System.out.println("Please be more specific.");
                break;
            default:
                System.out.println("You cannot do that.");
                break;
        }
    }
}
