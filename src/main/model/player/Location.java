package model.player;

import exceptions.InvalidActionException;

import java.util.HashMap;
import java.util.Map;

public class Location {

    private Map<String, String> objectsOfInterest = new HashMap<>();
    private Map<String, String> actionScenes = new HashMap<>();
    private String id;
    private String name;

    public Location(String id, String name, Map<String, Location> allLocations) {
        this.id = id;
        this.name = name;
        allLocations.put(id, this);
    }

    public void addObjectOfInterest(String synonym, String id) {
        objectsOfInterest.put(synonym, id);
    }

    public void addActionScene(String actionCode, String sceneId) {
        actionScenes.put(actionCode, sceneId);
    }

    public String getName() {
        return name;
    }

    public String getObjectOfInterest(String keyword) {
        return objectsOfInterest.get(keyword);
    }

    public String tryActionCode(String actionCode) throws InvalidActionException {
        String sceneId = actionScenes.get(actionCode);
        if (sceneId == null) {
            throw new InvalidActionException();
        }
        return sceneId;
    }
}
