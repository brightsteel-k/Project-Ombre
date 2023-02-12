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
                                + "swirl in a vortex of freezing amethyst. Your mind feels numb. You steel yourself \n"
                                + "and step into the cool stonebrick chamber, shielding your eyes from the shining "
                                + "purple crystal in front of you. A moment later, the blazing light fades and \n"
                                + "you're left blinking spots away at the front of a large heptagonal chamber. ",
                                "What were you doing, again?",
                                "The giant crystal in front of you sputters, a shifting maelstrom of violet energy and "
                                + "abyssal darkness mounted on a dark gray metal frame.",
                                "Right. The Voidstone.",
                                "An unnatural artifact constructed with forbidden shadow magic in an attempt to "
                                + "manipulate the very fabric of time. Built by an overzealous mage noir with \n"
                                + "demented aspirations of becoming the greatest magic user to ever live: Kallim "
                                + "Dythanos. ",
                                "You represent the efforts of the entire council of lord sorciers within the Azyrean "
                                + "Kingdom, who had gone to great lengths to locate and discreetly transport you to \n"
                                + "Dythanos’s lair just as the Voidstone began to act up.",
                                "Now here you are, in the heart of a dangerous rogue mage’s most ambitious project, "
                                + "with one goal in mind: destroy Dythanos and the Voidstone. "
                        }, "home"));
    }

    public static void printNextLine() {
        CURRENT_SCENE.printNextLine();
    }

    public static void beginScene(String id) {
        CURRENT_SCENE = ALL_SCENES.get(id);
        printNextLine();
    }

    public void start() {
        beginScene("intro");
    }
}
