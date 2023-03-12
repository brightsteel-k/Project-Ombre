package model;

import util.Deserializer;

import java.io.File;

// Handles saving and loading game states to and from the data folder
public class SaveSystem {

    public static final String SAVE_PATH = "data/saves/game0.json";
    private boolean saveDetected;
    private SaveState savedGame;

    // EFFECTS: saveDetected is true iff a file exists in the appropriate save directory
    public SaveSystem() {
        saveDetected = Deserializer.foundFile(SAVE_PATH);
    }

    public boolean isSaveDetected() {
        return saveDetected;
    }

    // REQUIRES: saveDetected == true
    // MODIFIES: this
    // EFFECTS: reads and deserializes the saved game state from the appropriate save directory, stores it in
    //          savedGame.
    public void loadGame() {
        savedGame = Deserializer.loadObject(SaveState.class, SAVE_PATH);
    }

    // EFFECTS: returns player stored in saved game
    public Player getPlayer() {
        return savedGame.getPlayer();
    }

    // EFFECTS: returns scene stored in saved game
    public String getCurrentScene() {
        return savedGame.getCurrentScene();
    }

    // EFFECTS: returns location stored in saved game
    public String getCurrentLocation() {
        return savedGame.getCurrentLocation();
    }

    // REQUIRES: currentState.length() > 0, currentLocation.length() > 0
    // MODIFIES: device disk
    // EFFECTS: serializes and writes saved game object to the appropriate save directory
    public void saveGame(Player player, String currentScene, String currentLocation) {
        SaveState state = new SaveState(player, currentScene, currentLocation);
        if (!saveDetected) {
            Deserializer.makeFile(SAVE_PATH);
            saveDetected = true;
        }
        Deserializer.writeObject(state, SAVE_PATH);
    }

    // EFFECTS: if and only if a save file exists at the appropriate save directory, delete the file
    public void deleteSave() {
        if (saveDetected) {
            File f = new File(SAVE_PATH);
            f.delete();
            saveDetected = false;
        }
    }

    // Single object to store all the relevant information about the current state of the game
    public class SaveState {
        private final Player player;
        private final String currentScene;
        private final String currentLocation;

        // EFFECTS: SaveState has given player, given id of current scene, and given id of current location
        public SaveState(Player player, String currentScene, String currentLocation) {
            this.player = player;
            this.currentScene = currentScene;
            this.currentLocation = currentLocation;
        }

        public Player getPlayer() {
            return player;
        }

        public String getCurrentScene() {
            return currentScene;
        }

        public String getCurrentLocation() {
            return currentLocation;
        }
    }
}
