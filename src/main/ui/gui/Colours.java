package ui.gui;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Colours {

    private static Map<String, Color> ALL_COLOURS = new HashMap<>();

    public static void initializeColours() {
        ALL_COLOURS.put("main_panel", new Color(0x151515));
        ALL_COLOURS.put("sidebar_panel", new Color(0x444444));
        ALL_COLOURS.put("console_background", new Color(0x2D2D2D));
        ALL_COLOURS.put("text_normal", new Color(0xD0D0D0));
        ALL_COLOURS.put("text_inactive", new Color(0x8C8C8C));
        ALL_COLOURS.put("input_background_active", new Color(0x2D2D2D));
        ALL_COLOURS.put("input_background_inactive", new Color(0x505050));
        ALL_COLOURS.put("main_border", new Color(0xAF7FBB));
    }

    public static Color getColour(String id) {
        return ALL_COLOURS.get(id);
    }
}
