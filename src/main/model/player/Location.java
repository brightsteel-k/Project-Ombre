package model.player;

import java.util.HashMap;
import java.util.Map;

public class Location {

    private Map<String, String> objectsOfInterest = new HashMap<>();
    private String id;

    public Location(String id, Map<String, Location> allLocations) {
        this.id = id;
        allLocations.put(id, this);
    }

    public void addObjectOfInterest(String synonym, String id) {
        objectsOfInterest.put(synonym, id);
    }


}
