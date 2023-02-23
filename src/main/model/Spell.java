package model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;

// Arcane action
public class Spell {
    private String keyword;
    private float damage;

    public Spell() {

    }

    public String getKeyword() {
        return keyword;
    }

    public float getDamage() {
        return damage;
    }
}
