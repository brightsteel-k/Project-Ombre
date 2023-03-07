package model;

import util.Deserializer;

public class SaveSystem {

    private final String savePath = "data/saves/game0.json";
    private boolean saveDetected;
    private SaveState savedGame;

    public SaveSystem() {
        saveDetected = Deserializer.foundFile(savePath);
    }

    public boolean isSaveDetected() {
        return saveDetected;
    }

    public void loadGame() {
        savedGame = Deserializer.loadObject(SaveState.class, savePath);
    }

    public Player getPlayer() {
        return savedGame.getPlayer();
    }

    public String getCurrentScene() {
        return savedGame.getCurrentScene();
    }

    public String getCurrentLocation() {
        return savedGame.getCurrentLocation();
    }

    public void saveGame(Player player, String currentState, String currentLocation) {
        SaveState state = new SaveState(player, currentState, currentLocation);
        if (saveDetected) {
            Deserializer.writeObject(state, savePath);
        } else {
            // TODO: Create file
        }
    }

    private class SaveState {
        private final Player player;
        private final String currentScene;
        private final String currentLocation;

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
