package ui.gui;

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

// Runs code from model, manages GUI, weaves everything together into a coherent experience
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

    // EFFECTS: Game has its own random number generator, save system, interpreter, and initialized story
    //          objects that will work together to present the user with a text-based adventure experience. It also
    //          records the main window and panels that comprise the GUI. Finally, it initializes Deserializer class
    //          and starts the game.
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

    // MODIFIES: this, this object's story instance, this object's player instance, this object's console panel instance
    // EFFECTS: prints the next line of the story's current scene to the console panel, or handles the end of the
    //          scene if necessary.
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
    // EFFECTS: offers player the choice to load a new game, iff one has been saved
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

    // MODIFIES: this, this object's story instance, this object's player instance, this object's console panel instance
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

    // REQUIRES: id is the valid id of a registered game scene
    // MODIFIES: this object's story instance, this object's console panel instance
    // EFFECTS: prepares story controller for the given scene, disables input to console, prints first line
    private void startScene(String id) {
        story.setCurrentScene(id);
        isExploring = false;
        consolePanel.setInputActive(false);
        printNextLine();
    }

    // MODIFIES: this, this object's console panel instance
    // EFFECTS: enabled input to console in preparation for user to explore
    private void startExploring() {
        isExploring = true;
        consolePanel.setInputActive(true);
        consolePanel.setWaitingForEnter(false);
    }

    // REQUIRES: userInput.length() > 0
    // MODIFIES: this, this object's story instance, this object's player instance, this object's console panel
    //           instance.
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

    // REQUIRES: newLocation is the valid id of a registered game location
    // MODIFIES: this object's story instance, this object's console panel instance
    // EFFECTS: updates player's location in the story, prints corresponding message
    private void changeLocation(String newLocation) {
        consolePanel.printLine(story.changeLocation(newLocation), false);
    }

    // MODIFIES: this, device disk
    // EFFECTS: saves current state of the game to disk using save system
    public void saveGameState() {
        story.writeValuesToSaveSystem(saveSystem);
        saved = true;
        JOptionPane.showMessageDialog(mainWindow, "Game successfully saved!");
    }

    // EFFECTS: returns all spells that this game's player instance knows
    public Spell[] getKnownSpells() {
        return player.getSpells();
    }
}
