package ui;

import exceptions.ActionSubjectException;
import exceptions.AmbiguousActionException;
import exceptions.InvalidActionException;
import exceptions.SceneEndingException;
import model.Player;
import model.SaveSystem;
import model.StoryController;
import model.Interpreter;
import model.storyobjects.SceneEvent;
import model.storyobjects.Spell;
import util.Deserializer;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Controls text and interface with which the user interacts, runs the model code together
public class Game {

    private final StoryController story;
    private final Interpreter interpreter;
    private final SaveSystem saveSystem;
    private static final Scanner SCANNER = new Scanner(System.in);
    private final Random random;
    private boolean isExploring = false;
    private Player player;

    // EFFECTS: Printer has its own random number generator, save system, interpreter, and initialized story objects
    //          that will work together to present the user with a text-based adventure experience. Initalizes
    //          Deserializer class.
    public Game() {
        Deserializer.initializeGson();
        random = new Random();
        saveSystem = new SaveSystem();
        interpreter = new Interpreter();
        story = new StoryController();
        story.setCurrentScene("intro");
        story.setCurrentLocation("front");
        startGame();
    }

    // MODIFIES: this, this object's story instance, this object's player instance, device disk
    // EFFECTS: starts the gameplay loop
    private void game() {
        String a = "🌟 This game does not have an autosave feature. Type the code 'savegame' to save your progress 🌟";
        System.out.println(a + "\n[Enter to continue]");
        SCANNER.nextLine();
        System.out.println("\n");
        while (true) {
            printScene();

            if (isExploring) {
                handleExploring();
            }
        }
    }

    // MODIFIES: this, this object's story instance, this object's player instance, device disk
    // EFFECTS: offers player the choice to load a new game, iff one has been saved; then, calls method to start
    //          gameplay loop.
    private void startGame() {
        if (!saveSystem.isSaveDetected()) {
            newGame();
        } else {
            System.out.println("New game (N) or continue (C) from saved game?");
            boolean waiting = true;
            while (waiting) {
                String input = SCANNER.nextLine();
                if (input.equalsIgnoreCase("n")) {
                    newGame();
                    waiting = false;
                } else if (input.equalsIgnoreCase("c")) {
                    loadGame();
                    waiting = false;
                }
            }
        }
        game();
    }

    // MODIFIES: this object's story instance, this object's player instance
    // EFFECTS: creates a new player, starting the game from the very beginning
    private void newGame() {
        player = new Player();
        story.setPlayer(player);
    }

    // REQUIRES: previous player object is already serialized to a Json file at the correct directory
    // MODIFIES: this object's story instance, this object's player instance
    // EFFECTS: loads player from previous Json file
    private void loadGame() {
        saveSystem.loadGame();
        player = saveSystem.getPlayer();
        story.setPlayer(player);
        story.setCurrentScene(saveSystem.getCurrentScene());
        story.setCurrentLocation(saveSystem.getCurrentLocation());
    }

    // MODIFIES: this, this object's story instance, this object's player instance
    // EFFECTS: prints the successive lines in an exposition scene, one by one, separated by a call to continueText()
    public void printScene() {
        boolean isPrinting = true;
        while (isPrinting) {
            try {
                System.out.println(story.getCurrentNextLine());
                continueText();
            } catch (SceneEndingException e) {
                isPrinting = false;
                handleSceneEvents(e.getEvents());
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
    @SuppressWarnings("methodlength")
    private void handleSceneEvent(SceneEvent event) {
        if (event.hasConditions() && !story.conditionsFulfilled(event.getConditions())) {
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
            case START_EXPLORING:
                isExploring = true;
                break;
            case NEXT_SCENE:
                story.setCurrentScene(event.getKeyword());
                isExploring = false;
                break;
        }
    }

    // EFFECTS: pauses program until player inputs something
    public void continueText() {
        System.out.print("[Enter to continue]");
        SCANNER.nextLine();
    }

    // MODIFIES: this object's story instance, this object's player instance, device disk
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

    // MODIFIES: this object's story instance, this object's player instance, device disk
    // EFFECTS: receives user input, passes it to the story object for formatting into an action code,
    //          carries the action out the action if valid. Else, throws exception.
    private void executeUserInput() throws InvalidActionException {
        String[] actionWords = interpreter.userInput(SCANNER.nextLine());

        if (actionWords[0].equals("savegame")) {
            story.writeValuesToSaveSystem(saveSystem);
            System.out.println("Game successfully saved!");
        } else if (actionWords[0].equals("meditate")) {
            printSpells();
        } else {
            handleSceneEvents(story.executeAction(actionWords));
        }
    }

    // MODIFIES: this object's story instance
    // EFFECTS: updates player's location in the story, prints corresponding message
    private void changeLocation(String newLocation) {
        System.out.println(story.changeLocation(newLocation));
    }

    // EFFECTS: prints info about all the spells the player has collected thus far, or a message about memory
    //          if they have collected none.
    private void printSpells() {
        Spell[] spells = player.getSpells();
        if (spells.length == 0) {
            String a = "You take a moment to collect yourself, trying to recall the esotaric shapes and \n"
                    + "incantations for the spells you know. However, a dark fog seems to clings to your mind\n"
                    + "obscuring your memory of the spells and training you know you have experienced.";
            System.out.println(a);
            return;
        }

        String b = "You take a moment to collect yourself, visualizing the esoteric shapes and recalling\n"
                + "the incantations for the spells you know. For some reason, a dark fog seems to cling to your\n"
                + "mind, obfuscating your memory of any spells beyond the following: \n";
        System.out.println(b);
        for (Spell s : spells) {
            System.out.println("[*]  " + s.getIncantation() + " - " + s.getDamage() + " damage");
        }
    }

    // EFFECTS: prints message telling the player their previous action was invalid, depending on the type of exception
    private void printInvalidAction(InvalidActionException e) {
        if (e.getClass() == ActionSubjectException.class) {
            String invalidObject = ((ActionSubjectException) e).getInvalidObject();
            if (random.nextBoolean()) {
                System.out.println("You cannot reach any " + invalidObject + " from where you are.");
            } else {
                System.out.println("There is no " + invalidObject + " where you are.");
            }
        } else if (e.getClass() == AmbiguousActionException.class) {
            System.out.println("Please be more specific.");
        } else {
            System.out.println("You cannot do that.");
        }
    }
}
