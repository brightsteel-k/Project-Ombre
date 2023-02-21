package model.player;

import java.util.HashMap;
import java.util.Map;

public class Location {

    private Map<String, String> objectsOfInterest = new HashMap<>();
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

    public String getName() {
        return name;
    }
}
