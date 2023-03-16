package util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// Handles automatic deserialization of data objects like locations, spells, scenes, scene events, and scene conditions,
// as well as maps of synonyms. These make up the material of the story and game itself.
public class DataManager {

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

    // MODIFIES: this
    // EFFECTS: if it's null, initializes the Gson object that deserializes Json text and obeys the custom
    //          Exclude annotation.
    public static void initializeGson() {
        if (GSON == null) {
            GSON = new GsonBuilder().setExclusionStrategies(GSON_STRATEGY).create();
        }
    }

    // REQUIRES: pathName leads to a folder in the project data folder exclusively containing .json files that have
    //           been formatted to encode objects of the type classType.
    // MODIFIES: finalMap
    // EFFECTS: reads and deserializes all files in the given data folder, maps each resulting object to its name
    public static <T> void loadObjectsToMap(Class<T> classType, String pathName, Map<String, T> finalMap) {
        File directory = new File(pathName);
        String[] fileNames = directory.list();

        for (String name : fileNames) {
            String key = name.substring(0, name.length() - 5);
            T classObject = GSON.fromJson(readFile(pathName + "/" + name), classType);
            finalMap.put(key, classObject);
        }
    }

    // REQUIRES: pathName leads to a .json file that has been formatted to encode a map with values and keys that are
    //           Strings.
    // MODIFIES: finalMap
    // EFFECTS: reads and deserializes given data file, adds all internal mapped values to given map
    public static void loadSynonymsToMap(String pathName, Map<String, String> finalMap) {
        Map<String, String> readData = GSON.fromJson(readFile(pathName), HashMap.class);
        finalMap.putAll(readData);
    }

    // REQUIRES: pathName leads to a .json file that has been formatted to encode an object of the type classType
    // EFFECTS: reads and deserializes given data file, returns resulting object
    public static <T> T loadObject(Class<T> classType, String pathName) {
        return GSON.fromJson(readFile(pathName), classType);
    }

    // EFFECTS: returns true iff the given path leads to an existing file
    public static boolean foundFile(String path) {
        try {
            Files.readAllBytes(Paths.get(path));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // REQUIRES: path leads to an existing file
    // MODIFIES: device disk
    // EFFECTS: serializes the given object using Json, writes the encoded String into the given file
    public static <T> void writeObject(T obj, String pathName) {
        writeFile(pathName, GSON.toJson(obj));
    }

    // REQUIRES: path leads to a place on device where a directory & file can be made
    // MODIFIES: device disk
    // EFFECTS: creates a new file at the given path
    public static void makeFile(String pathName) {
        File f = new File(pathName);
        try {
            f.createNewFile();
        } catch (Exception e) {
            throw new RuntimeException("DataManager.makeFile() failed. Path: " + pathName, e);
        }
    }

    // REQUIRES: path leads to an existing file
    // EFFECTS: returns the contents of the given file, assuming it was encoded text, in a String
    public static String readFile(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("DataManager.readFile() failed. Path: " + path, e);
        }
    }

    // REQUIRES: path leads to an existing file
    // MODIFIES: device disk
    // EFFECTS: write the given contents String to the given file
    public static void writeFile(String path, String contents) {
        byte[] encoded = contents.getBytes(StandardCharsets.UTF_8);
        try {
            Files.write(Paths.get(path), encoded);
        } catch (Exception e) {
            throw new RuntimeException("DataManager.writeFile() failed. Path: " + path, e);
        }
    }
}
