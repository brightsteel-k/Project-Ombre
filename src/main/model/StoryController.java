package model;


import exceptions.*;
import model.storyobjects.Location;
import model.storyobjects.Spell;
import model.storyobjects.*;
import util.DataManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Controls information about the scenes, locations, and story of the adventure game
public class StoryController {

    public static final Map<String, Scene> ALL_SCENES = new HashMap<>();
    public static final Map<String, Location> ALL_LOCATIONS = new HashMap<>();
    public static final Map<String, Spell> ALL_SPELLS = new HashMap<>();
    private String currentSceneId;
    private String currentLocationId;
    private Scene currentScene;
    private Location currentLocation;
    private Player player;

    // MODIFIES: StoryController.ALL_SPELLS, StoryController.ALL_SCENES, StoryController.ALL_LOCATIONS
    // EFFECTS: StoryController has a reference to the player instance, and a master map of all scenes, locations,
    //          and spells in the game.
    public StoryController() {
        registerObjects();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    // MODIFIES: device disk
    // EFFECTS: sends current state of game to the save system to write to disk
    public void writeValuesToSaveSystem(SaveSystem system) {
        system.saveGame(player, currentSceneId, currentLocationId);
    }

    // MODIFIES: this
    // EFFECTS: sets current scene to the one registered to the corresponding ID and resets it,
    //          or throws exception if one does not exist.
    public void setCurrentScene(String id) {
        Scene nextScene = ALL_SCENES.get(id);
        if (nextScene == null) {
            throw new InvalidSceneException();
        }
        currentScene = nextScene;
        currentSceneId = id;
        currentScene.startScene();
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    // MODIFIES: this
    // EFFECTS: sets current location to the one registered to the corresponding ID, or throws exception if one
    //          does not exist.
    public void setCurrentLocation(String id) {
        Location nextLocation = ALL_LOCATIONS.get(id);
        if (nextLocation == null) {
            throw new InvalidLocationException();
        }
        currentLocationId = id;
        currentLocation = nextLocation;
    }

    // MODIFIES: StoryController.ALL_SPELLS, StoryController.ALL_SCENES, StoryController.ALL_LOCATIONS
    // EFFECTS: populates master maps of all spells, scenes, and locations in the game
    private void registerObjects() {
        DataManager.loadObjectsToMap(Spell.class, "data/spells", ALL_SPELLS);
        DataManager.loadObjectsToMap(Scene.class, "data/scenes", ALL_SCENES);
        DataManager.loadObjectsToMap(Location.class, "data/locations", ALL_LOCATIONS);
    }

    // MODIFIES: this
    // EFFECTS: returns the next line of the current scene and prepares the next line,
    //          or throws an exception if the scene has ended.
    public String getCurrentNextLine() throws SceneEndingException {
        return currentScene.getNextLine();
    }

    // REQUIRES: loc is a valid location in ALL_LOCATIONS
    // MODIFIES: this
    // EFFECTS: changes the current location to the one with the given id, returns the appropriate feedback message
    public String changeLocation(String id) {
        String previousName = currentLocation.getName();
        setCurrentLocation(id);
        return "You leave " + previousName + " and make your way to " + currentLocation.getName() + ".";
    }

    // REQUIRES: actionWords.length == 1 or 2
    // EFFECTS: if current location recognizes given actionWords as a valid input, returns the corresponding scene
    //          events to trigger in response. Otherwise, throws exception.
    public List<SceneEvent> executeAction(String[] actionWords) throws InvalidActionException {
        return currentLocation.tryActionCode(constructActionCode(actionWords));
    }

    // REQUIRES: actionWords.length == 1 or 2
    // EFFECTS: returns action code from the given input keywords, in the format of action@object. If the keywords act
    //          on an object that does not exist in the current location, throws specialized exception with message.
    private String constructActionCode(String[] actionWords) throws InvalidActionException {
        String actionCode;
        if (actionWords.length == 1) {
            actionCode = actionWords[0];
        } else {
            String obj = currentLocation.getObjectOfInterest(actionWords[1]);
            if (obj == null) {
                throw new ActionSubjectException(actionWords[1]);
            }
            actionCode = actionWords[0] + "@" + obj;
        }

        return actionCode;
    }

    // REQUIRES: conditions.length > 0
    // EFFECTS: returns true iff all the given conditions are met, based on the state of the player
    public boolean conditionsFulfilled(SceneEventCondition[] conditions) {
        for (SceneEventCondition cond : conditions) {
            if (!conditionFulfilled(cond)) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: returns true iff the given condition is met, based on the state of the player
    public boolean conditionFulfilled(SceneEventCondition condition) {
        switch (condition.getKey()) {
            case "@hasItem":
                return player.hasItem(condition.getExpected());
            case "@hasSpell":
                return player.hasSpell(condition.getExpected());
            case "@missingItem":
                return !player.hasItem(condition.getExpected());
            case "@missingSpell":
                return !player.hasSpell(condition.getExpected());
            default:
                return player.conditionMet(condition.getKey(), condition.getExpected());
        }
    }
}
