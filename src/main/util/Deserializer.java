package util;

import com.google.gson.Gson;
import model.Spell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Deserializer {

    private static final Gson GSON = new Gson();

    // TODO: Add GSON dependency to lib: https://github.com/google/gson
    public static void loadSpells(Map<String, Spell> spellMap) {
        String path = "data/spells";
        File directory = new File(path);
        String[] fileNames = directory.list();

        for (String name : fileNames) {
            String key = name.substring(0, name.length() - 5);
            Spell spellObject = GSON.fromJson(readFile("data/spells/" + name), Spell.class);
            spellMap.put(key, spellObject);
        }
    }

    private static String readFile(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
