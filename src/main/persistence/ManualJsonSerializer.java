package persistence;

import model.Player;
import model.SaveSystem;
import model.storyobjects.Spell;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class ManualJsonSerializer {

    // EFFECTS: serializes the given gamestate object into JSON and returns the result in a String
    public static String saveStateToJson(SaveSystem.SaveState saveState) {
        return serializeSaveState(saveState).toString(4);
    }

    // EFFECTS: serializes the given gamestate object into a JSON object; returns the result
    private static JSONObject serializeSaveState(SaveSystem.SaveState saveState) {
        JSONObject stateObject = new JSONObject();
        stateObject.put("currentScene", saveState.getCurrentScene());
        stateObject.put("currentLocation", saveState.getCurrentLocation());
        stateObject.put("player", serializePlayer(saveState.getPlayer()));
        return stateObject;
    }

    // EFFECTS: serializes the given player object into a JSON object storing its progress conditions, items, and
    //          spells; returns the result.
    private static JSONObject serializePlayer(Player player) {
        JSONObject playerObject = new JSONObject();
        playerObject.put("progressConditions", serializeStringMap(player.getProgressConditions()));
        playerObject.put("items", serializeStringList(player.getItems()));
        playerObject.put("spells", serializeSpellArray(player.getSpells()));
        return playerObject;
    }

    // EFFECTS: serializes the given map of strings into a JSON object; returns the result
    private static JSONObject serializeStringMap(Map<String, String> stringMap) {
        return new JSONObject(stringMap);
    }

    // EFFECTS: serializes the given list of strings into a JSON array; returns the result
    private static JSONArray serializeStringList(List<String> strings) {
        JSONArray arrayObject = new JSONArray();
        arrayObject.putAll(strings);
        return arrayObject;
    }

    // EFFECTS: serializes the given list of spells into a JSON array; returns the result
    private static JSONArray serializeSpellArray(Spell[] spells) {
        JSONArray arrayObject = new JSONArray();
        for (Spell s : spells) {
            arrayObject.put(serializeSpell(s));
        }
        return arrayObject;
    }

    // EFFECTS: serializes the given spell into a JSON object storing its name, incantation, school,
    //          description, and damage; returns the result.
    private static JSONObject serializeSpell(Spell spell) {
        JSONObject spellObject = new JSONObject();
        spellObject.put("name", spell.getName());
        spellObject.put("incantation", spell.getIncantation());
        spellObject.put("school", spell.getSchool());
        spellObject.put("description", spell.getDescription());
        spellObject.put("damage", spell.getDamage());
        return spellObject;
    }
}
