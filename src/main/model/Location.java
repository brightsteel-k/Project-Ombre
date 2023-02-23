package model;

import exceptions.InvalidActionException;
import model.scenes.SceneEvent;

import java.util.*;

// A distinct in-game destination that the player can occupy, in which there are sets of objects and actions to use
// and interact with.
public class Location {

    private Map<String, String> objectsOfInterest = new HashMap<>();
    private Map<String, List<SceneEvent>> actionEvents = new HashMap<>();
    private String id;
    private String name;

    public Location(String id, String name, Map<String, Location> allLocations) {
        this.id = id;
        this.name = name;
        allLocations.put(id, this);
    }

    // REQUIRES: synonym.length() > 1, synonym.length() > 1
    // MODIFIES: this
    // EFFECTS: records an alternate name for a specific object id
    public void addObjectOfInterest(String synonym, String id) {
        objectsOfInterest.put(synonym, id);
    }

    // REQUIRES: actionCode.length() > 1, sceneEventsIn.length >= 1
    // MODIFIES: this
    // EFFECTS: records a specific action code that triggers the given scene events
    public void addActionEvent(String actionCode, SceneEvent[] sceneEventsIn) {
        List<SceneEvent> sceneEvents = new ArrayList<>(Arrays.asList(sceneEventsIn));
        actionEvents.put(actionCode, sceneEvents);
    }

    public String getName() {
        return name;
    }

    public String getObjectOfInterest(String keyword) {
        return objectsOfInterest.get(keyword);
    }

    // EFFECTS: returns the scene events triggered by the given action code, or throws exception if there are none
    public List<SceneEvent> tryActionCode(String actionCode) throws InvalidActionException {
        List<SceneEvent> events = actionEvents.get(actionCode);
        if (events == null) {
            throw new InvalidActionException();
        }
        return events;
    }
}
