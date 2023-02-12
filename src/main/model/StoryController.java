package model;


import ui.Printer;

import javax.swing.plaf.ColorUIResource;
import java.util.HashMap;
import java.util.Map;

// Controls actual information and story of the adventure game
public class StoryController {

    public static final Map<String, Scene> ALL_SCENES = new HashMap<>();
    public static final Printer PRINTER = new Printer();
    private static Scene CURRENT_SCENE;

    public StoryController() {
        initializeScenes();
    }

    public Scene getCurrentScene() {
        return CURRENT_SCENE;
    }

    public void setCurrentScene(Scene scene) {
        CURRENT_SCENE = scene;
    }

    private void initializeScenes() {
        ALL_SCENES.put("intro",
                new DescriptiveScene(
                        new String[]{"A flash of brilliant violet light blinds your vision, making your head "
                                + "swirl in a vortex of freezing amethyst. Your mind feels numb. ",
                                "You steel yourself and step into the cool stonebrick chamber, shielding your eyes "
                                + "from the shining purple crystal in front of you. A moment later, the blazing light "
                                + "fades and you're left blinking spots away at the front of a large heptagonal "
                                + "chamber. ",
                                "What were you doing, again?"
                        }, "home"));
    }

    public static void printNextLine() {
        CURRENT_SCENE.printNextLine();
    }

    public void start() {
        setCurrentScene(ALL_SCENES.get("intro"));
        printNextLine();
    }
}
