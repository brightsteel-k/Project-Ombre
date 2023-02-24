package util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Spell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Deserializer {

    private static Gson GSON;
    private static final ExclusionStrategy GSON_STRATEGY = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return fieldAttributes.getAnnotation(Exclude.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> classType) {
            return false;
        }
    };

    public static void initializeGson() {
        GSON = new GsonBuilder().setExclusionStrategies(GSON_STRATEGY).create();
    }

    // TODO: Add GSON dependency to lib: https://github.com/google/gson
    public static <T> void loadObjectsToMap(Class<T> classType, String pathName, Map<String, T> finalMap) {
        File directory = new File(pathName);
        String[] fileNames = directory.list();

        for (String name : fileNames) {
            String key = name.substring(0, name.length() - 5);
            T classObject = GSON.fromJson(readFile(pathName + "/" + name), classType);
            finalMap.put(key, classObject);
        }
    }

    public static void loadSynonymsToMap(String pathName, Map<String, String> finalMap) {
        Map<String, String> readData = GSON.fromJson(readFile(pathName), HashMap.class);
        finalMap.putAll(readData);
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
