package persistence;

import model.Player;
import model.SaveSystem;
import model.storyobjects.Spell;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class ManualJsonDeserializer {

    public static SaveSystem.SaveState loadStateFromJson(String data, SaveSystem system) {
        JSONObject stateObject = new JSONObject(data);
        return deserializeSaveState(stateObject, system);
    }

    // EFFECTS: deserializes the given JSON object into a gamestate; returns the result
    private static SaveSystem.SaveState deserializeSaveState(JSONObject stateObject, SaveSystem system) {
        String currentScene = stateObject.getString("currentScene");
        String currentLocation = stateObject.getString("currentLocation");
        Player player = deserializePlayer(stateObject.getJSONObject("player"));
        return system.new SaveState(player, currentScene, currentLocation);
    }

    // EFFECTS: deserializes the given JSON object into a player object with progress conditions, items, and
    //          spells; returns the result.
    private static Player deserializePlayer(JSONObject playerObject) {
        Map<String, String> progressConditions = deserializeStringMap(playerObject.getJSONObject("progressConditions"));
        List<String> items = deserializeStringList(playerObject.getJSONArray("items"));
        Map<String, Spell> spells = deserializeSpellMap(playerObject.getJSONArray("spells"));

        return new Player(progressConditions, items, spells);
    }

    // EFFECTS: deserializes the given JSON object into a map of strings; returns the result
    private static Map<String, String> deserializeStringMap(JSONObject mapObject) {
        Map<String, String> progressConditions = new HashMap<>();
        Set<String> keys = mapObject.keySet();
        for (String k : keys) {
            progressConditions.put(k, mapObject.getString(k));
        }
        return progressConditions;
    }

    // EFFECTS: deserializes the given JSON array into a list of strings; returns the result
    private static List<String> deserializeStringList(JSONArray arrayObject) {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < arrayObject.length(); i++) {
            items.add(arrayObject.getString(i));
        }
        return items;
    }

    // EFFECTS: deserializes the given JSON array into a list of spells; maps them out based on their incantations;
    //          returns the result.
    private static Map<String, Spell> deserializeSpellMap(JSONArray arrayObject) {
        Map<String, Spell> spells = new HashMap<>();
        for (int i = 0; i < arrayObject.length(); i++) {
            Spell spell = deserializeSpell(arrayObject.getJSONObject(i));
            spells.put(spell.getIncantation(), spell);
        }
        return spells;
    }

    // EFFECTS: deserializes the given JSON object into a spell that has a name, incantation, school,
    //          description, and damage; returns the result.
    private static Spell deserializeSpell(JSONObject spellObject) {
        String name = spellObject.getString("name");
        String incantation = spellObject.getString("incantation");
        String school = spellObject.getString("school");
        String description = spellObject.getString("description");
        float damage = spellObject.getFloat("damage");
        return new Spell(name, incantation, school, description, damage);
    }
}
