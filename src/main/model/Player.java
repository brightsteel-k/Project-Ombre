package model;

import model.storyobjects.Spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Keeps track of player's stats, items, and spells
public class Player {

    private Map<String, String> progressConditions;
    private List<String> items;
    private Map<String, Spell> spells;

    // EFFECTS: Player is initialized with empty collections of conditions, items, and spells.
    public Player() {
        progressConditions = new HashMap<>();
        items = new ArrayList<>();
        spells = new HashMap<>();
    }

    // EFFECTS: Player is initialized with empty collections of conditions, items, and spells.
    public Player(Map<String, String> progressConditions, List<String> items, Map<String, Spell> spells) {
        this.progressConditions = progressConditions;
        this.items = items;
        this.spells = spells;
    }

    // EFFECTS: returns all the spells this player knows
    public Spell[] getSpells() {
        return spells.values().toArray(new Spell[0]);
    }

    public List<String> getItems() {
        return items;
    }

    public Map<String, String> getProgressConditions() {
        return progressConditions;
    }

    // REQUIRES: name.length() > 0
    // MODIFIES: this
    // EFFECTS: adds the item name to the player's list of acquired items
    public void addItem(String name) {
        items.add(name);
    }

    // REQUIRES: name.length() > 0
    // EFFECTS: returns true iff the player's list of acquired items contains the given item name
    public boolean hasItem(String name) {
        return items.contains(name);
    }

    // REQUIRES: name.length() > 0
    // MODIFIES: this
    // EFFECTS: adds the spell with the given name to the player's collection of known spells, or does nothing if
    //          it's already there.
    public void addSpell(String name) {
        if (hasSpell(name)) {
            return;
        }
        spells.put(name, StoryController.ALL_SPELLS.get(name));
    }

    // REQUIRES: name.length() > 0
    // EFFECTS: returns true iff the player's collection of known spells contains the given spell name
    public boolean hasSpell(String name) {
        return spells.containsKey(name);
    }

    // REQUIRES: key.length() > 0, value.length() > 0
    // MODIFIES: this
    // EFFECTS: adds an entry to the player's map of conditions with the given key name and given value, or gives
    //          the entry with the given key name the given value if the condition already exists.
    public void setCondition(String key, String value) {
        progressConditions.put(key, value);
    }

    // REQUIRES: key.length() > 0, value.length() > 0
    // EFFECTS: returns true iff the player's map of conditions contains one with the given key, and it has the given
    //          expected value.
    public boolean conditionMet(String key, String expected) {
        String value = progressConditions.get(key);
        if (expected.equals("@f") && value == null) {
            return true;
        }
        return value != null && value.equals(expected);
    }
}
