package model.player;


import exceptions.InvalidActionException;
import exceptions.InvalidSceneException;
import exceptions.SceneEndingException;
import model.Location;
import model.Spell;
import model.scenes.*;
import util.Deserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Controls information about the scenes, locations, and story of the adventure game
public class StoryController {

    public static final Map<String, Scene> ALL_SCENES = new HashMap<>();
    public static final Map<String, Location> ALL_LOCATIONS = new HashMap<>();
    public static final Map<String, Spell> ALL_SPELLS = new HashMap<>();
    private static Scene CURRENT_SCENE;
    private static Location CURRENT_LOCATION;
    private final Player player;

    public StoryController(Player player) {
        this.player = player;
        initializeObjects();
    }

    public Scene getCurrentScene() {
        return CURRENT_SCENE;
    }

    // MODIFIES: this
    // EFFECTS: sets current scene to the one with the corresponding id,
    //          OR throws exception if one does not exist.
    public void setCurrentScene(String id) {
        Scene nextScene = ALL_SCENES.get(id);
        if (nextScene == null) {
            throw new InvalidSceneException();
        }
        CURRENT_SCENE = nextScene;
    }

    public Location getCurrentLocation() {
        return CURRENT_LOCATION;
    }

    public void setCurrentLocation(String id) {
        CURRENT_LOCATION = ALL_LOCATIONS.get(id);
    }

    public Location getLocation(String id) {
        return ALL_LOCATIONS.get(id);
    }

    private void initializeObjects() {
        Deserializer.loadObjectsToMap(Spell.class, "data/spells", ALL_SPELLS);
        Deserializer.loadObjectsToMap(Scene.class, "data/scenes", ALL_SCENES);
        Deserializer.loadObjectsToMap(Location.class, "data/locations", ALL_LOCATIONS);
    }

    // EFFECTS: returns the next line of the current scene, or throws an exception if the scene has ended
    public String getCurrentNextLine() throws SceneEndingException {
        return CURRENT_SCENE.getNextLine();
    }

    // REQUIRES: loc is a valid location in ALL_LOCATIONS
    // MODIFIES: this
    // EFFECTS: changes the current location to the one with the given id, returns the appropriate feedback message
    public String changeLocation(String loc) {
        String previousName = CURRENT_LOCATION.getName();
        setCurrentLocation(loc);
        return "You leave " + previousName + " and make your way to " + CURRENT_LOCATION.getName() + ".";
    }

    // REQUIRES: actionWords.length == 1 or 2
    // EFFECTS: if current location recognizes given actionWords as a valid input, returns the corresponding scene
    //          events to trigger in response. Otherwise, throws exception.
    public List<SceneEvent> executeAction(String[] actionWords) throws InvalidActionException {
        return CURRENT_LOCATION.tryActionCode(constructActionCode(actionWords));
    }

    // REQUIRES: actionWords.length == 1 or 2
    // EFFECTS: returns action code from the given input keywords, in the format of action@object. If the keywords act
    //          on an object that does not exist in the current location, throws specialized exception with message.
    private String constructActionCode(String[] actionWords) throws InvalidActionException {
        String actionCode;
        if (actionWords.length == 1) {
            actionCode = actionWords[0];
        } else {
            String obj = CURRENT_LOCATION.getObjectOfInterest(actionWords[1]);
            if (obj == null) {
                throw new InvalidActionException(1, actionWords[1]);
            }
            actionCode = actionWords[0] + "@" + obj;
        }

        return actionCode;
    }

    // EFFECTS: returns true if the given condition is met, based on state of values in the Player class
    public boolean conditionFulfilled(SceneEventCondition condition) {
        switch (condition.getKey()) {
            case "#hasItem":
                return player.hasItem(condition.getExpected());
            case "#hasSpell":
                return player.hasSpell(condition.getExpected());
            default:
                return player.conditionMet(condition.getKey(), condition.getExpected());
        }
    }
}
