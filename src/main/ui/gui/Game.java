package ui.gui;

import exceptions.ActionSubjectException;
import exceptions.AmbiguousActionException;
import exceptions.InvalidActionException;
import exceptions.SceneEndingException;
import model.Interpreter;
import model.Player;
import model.SaveSystem;
import model.StoryController;
import model.storyobjects.SceneEvent;
import model.storyobjects.Spell;
import util.DataManager;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class Game {
    private final MainWindow mainWindow;
    private final StoryController story;
    private final Interpreter interpreter;
    private final SaveSystem saveSystem;
    private final ConsolePanel consolePanel;
    private final Random random;
    private boolean isExploring = false;
    private Player player;
    private boolean saved = true;


    public Game() {
        DataManager.initializeGson();
        random = new Random();
        saveSystem = new SaveSystem();
        interpreter = new Interpreter();
        story = new StoryController();
        story.setCurrentScene("intro");
        story.setCurrentLocation("front");
        mainWindow = new MainWindow(this);
        consolePanel = mainWindow.getConsolePanel().setGame(this);
        mainWindow.getSidebarPanel().setGame(this);
        getPlayer();
        startGame();
    }

    public boolean hasSaved() {
        return saved;
    }

    void printNextLine() {
        try {
            consolePanel.printLine(story.getCurrentNextLine(), true);
        } catch (SceneEndingException e) {
            handleSceneEvents(e.getEvents());
        }
    }

    // MODIFIES: this, this object's story instance, this object's player instance, device disk
    // EFFECTS: starts the gameplay loop
    private void startGame() {
        String a = "🌟 This game does not have an autosave feature. Use the save button at the top-right to save "
                + "your progress 🌟";
        consolePanel.printLine(a, true);
    }

    // MODIFIES: this, this object's story instance, this object's player instance, device disk
    // EFFECTS: offers player the choice to load a new game, iff one has been saved; then, calls method to start
    //          gameplay loop.
    private void getPlayer() {
        if (!saveSystem.isSaveDetected()) {
            newGame();
        } else {
            int i = JOptionPane.showConfirmDialog(mainWindow, "Load game from save?",
                    "Save file detected!", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if (i == JOptionPane.YES_OPTION) {
                loadGame();
            } else {
                newGame();
            }
        }
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
                consolePanel.printLine(event.getKeyword(), false);
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
                startExploring();
                break;
            case NEXT_SCENE:
                startScene(event.getKeyword());
                break;
        }
    }

    private void startScene(String id) {
        story.setCurrentScene(id);
        isExploring = false;
        consolePanel.setInputActive(false);
        printNextLine();
    }


    private void startExploring() {
        isExploring = true;
        consolePanel.setInputActive(true);
        consolePanel.setWaitingForEnter(false);
    }


    public boolean executeUserInput(String userInput) {
        saved = false;
        try {
            String[] actionWords = interpreter.userInput(userInput);
            handleSceneEvents(story.executeAction(actionWords));
        } catch (InvalidActionException e) {
            return false;
        }
        return true;
    }

    // MODIFIES: this object's story instance
    // EFFECTS: updates player's location in the story, prints corresponding message
    private void changeLocation(String newLocation) {
        consolePanel.printLine(story.changeLocation(newLocation), false);
    }

    public void saveGameState() {
        //story.writeValuesToSaveSystem(saveSystem);
        JOptionPane.showMessageDialog(mainWindow, "Game successfully saved!");
    }

    // EFFECTS: prints info about all the spells the player has collected thus far, or a message about memory
    //          if they have collected none.
    private void printSpells() {
        Spell[] spells = player.getSpells();
        if (spells.length == 0) {
            String a = "You take a moment to collect yourself, trying to recall the esoteric shapes and \n"
                    + "incantations for the spells you know. However, a dark fog seems to clings to your mind,\n"
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
}
