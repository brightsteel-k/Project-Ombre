package model.player;

import model.Spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Keeps track of player's stats, items, and spells
public class Player {

    private Map<String, String> progressConditions = new HashMap<>();
    private List<String> items = new ArrayList<>();
    private Map<String, Spell> spells = new HashMap<>();

    public Player() {

    }

    public boolean addItem(String name) {
        return items.add(name);
    }

    public boolean hasItem(String name) {
        return items.contains(name);
    }

    // REQUIRES: name.length() > 1
    // MODIFIES: this
    // EFFECTS: adds the spell with the given name to the Player's collection of known spells, or does nothing if
    //          it's already there.
    public void addSpell(String name) {
        if (hasSpell(name)) {
            return;
        }
        spells.put(name, StoryController.ALL_SPELLS.get(name));
    }

    public boolean hasSpell(String name) {
        return spells.containsKey(name);
    }

    public void setCondition(String key, String value) {
        progressConditions.put(key, value);
    }

    public boolean conditionMet(String key, String expected) {
        String value = progressConditions.get(key);
        return value != null && value.equals(expected);
    }
}
