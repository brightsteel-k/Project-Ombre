package model.storyobjects;

import exceptions.InvalidActionException;
import model.storyobjects.SceneEvent;

import java.util.*;

// A distinct in-game destination that the player can occupy, in which there are sets of objects and actions to use
// and interact with.
public class Location {

    private String name;
    private Map<String, String> objectsOfInterest = new HashMap<>();
    private Map<String, List<SceneEvent>> actionEvents = new HashMap<>();

    public String getName() {
        return name;
    }

    // REQUIRES: objectsOfInterest.containsKey(keyword)
    // EFFECTS: returns translation of the given word to a corresponding object id
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

    // EFFECTS: returns true iff this and the given object are identical Locations
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Location.class) {
            return false;
        }
        Location other = (Location)obj;
        return name.equals(other.name) && objectsOfInterest.equals(other.objectsOfInterest)
                && actionEvents.equals(other.actionEvents);
    }
}
