package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

// A helper class to keep track of all GUI colours and operations on them
public class Colours {

    private static Map<String, Color> ALL_COLOURS = new HashMap<>();

    public static void initializeColours() {
        ALL_COLOURS.put("main_panel", new Color(0x151515));
        ALL_COLOURS.put("sidebar_panel", new Color(0x444444));
        ALL_COLOURS.put("sidebar_button", new Color(0x606060));
        ALL_COLOURS.put("console_background", new Color(0x2D2D2D));
        ALL_COLOURS.put("text_normal", new Color(0xD0D0D0));
        ALL_COLOURS.put("text_inactive", new Color(0x8C8C8C));
        ALL_COLOURS.put("text_invalid", new Color(0xEF2E2E));
        ALL_COLOURS.put("input_background_active", new Color(0x2D2D2D));
        ALL_COLOURS.put("input_background_inactive", new Color(0x505050));
        ALL_COLOURS.put("main_border", new Color(0xAF7FBB));
        ALL_COLOURS.put("spells_panel_background", new Color(0x444444));
        ALL_COLOURS.put("spells_panel_description", new Color(0x2F2F2F));
        ALL_COLOURS.put("spells_panel_button", new Color(0x343434));
        ALL_COLOURS.put("spell_ember", new Color(0xFFBEA6));
        ALL_COLOURS.put("spell_breath", new Color(0xE2FFFC));
        ALL_COLOURS.put("spell_silk", new Color(0xDCCEFF));
        ALL_COLOURS.put("spell_sand", new Color(0xF8E29E));
        ALL_COLOURS.put("spell_iron", new Color(0xB4B6C0));
        ALL_COLOURS.put("spell_blood", new Color(0xCB9191));
        ALL_COLOURS.put("spell_shadow", new Color(0x774E98));
    }

    public static Color getColour(String id) {
        return ALL_COLOURS.get(id);
    }

    // EFFECTS: linearly interpolates between the two given colours, returns colour at t% between them.
    public static Color lerp(Color color1, Color color2, double t) {
        int r0 = color1.getRed();
        int g0 = color1.getGreen();
        int b0 = color1.getBlue();


        int dr = (int)((color2.getRed() - r0) * t);
        int dg = (int)((color2.getGreen() - g0) * t);
        int db = (int)((color2.getBlue() - b0) * t);
        return new Color(r0 + dr, g0 + dg, b0 + db);
    }

    private double flashProgress;
    private Timer timer;

    // EFFECTS: Colours instance is made
    public Colours() {

    }

    // MODIFIES: this, component
    // EFFECTS: calls flashColour using the colours in ALL_COLOURS with the given ids
    public void flashColour(Component component, String original, String flash) {
        flashColour(component, getColour(original), getColour(flash));
    }

    // MODIFIES: this, component
    // EFFECTS: uses Timer to quickly interpolate the foreground colour of the component from original to flash and
    //          back according to a cosine curve.
    public void flashColour(Component component, Color original, Color flash) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                component.setForeground(lerp(flash, original, 0.5 * (Math.cos(flashProgress * Math.PI / 20) + 1)));
                if (flashProgress++ >= 40) {
                    timer.stop();
                }
            }
        };

        flashProgress = 0;
        timer = new Timer(1, al);
        timer.start();
    }
}
