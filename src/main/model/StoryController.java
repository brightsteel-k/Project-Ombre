package model;


import exceptions.InvalidActionException;
import exceptions.NoNextSceneException;
import exceptions.SceneEndingException;
import model.player.Location;
import model.scenes.ChoiceScene;
import model.scenes.DescriptiveScene;
import model.scenes.Scene;
import ui.Printer;

import java.util.HashMap;
import java.util.Map;

// Controls actual information and story of the adventure game
public class StoryController {

    public static final Map<String, Scene> ALL_SCENES = new HashMap<>();
    public static final Map<String, Location> ALL_LOCATIONS = new HashMap<>();
    private static Scene CURRENT_SCENE;
    private static Location CURRENT_LOCATION;

    public StoryController() {
        initializeScenes();
        initializeLocations();
    }

    public Scene getCurrentScene() {
        return CURRENT_SCENE;
    }

    public void setCurrentScene(String id) {
        CURRENT_SCENE = ALL_SCENES.get(id);
    }

    public Location getCurrentLocation() {
        return CURRENT_LOCATION;
    }

    public void setCurrentLocation(String id) {
        CURRENT_LOCATION = ALL_LOCATIONS.get(id);
    }

    // MODIFIES: this
    // EFFECTS: Defines all the scenes in the game, puts them into one giant map.
    //          Eventually will read data from serialized json files instead.
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
        ALL_SCENES.put("home", new ChoiceScene(new String[] { "Hello there." }));
    }

    private void initializeLocations() {
        Location front = new Location("front", ALL_LOCATIONS);
        front.addObjectOfInterest("desk", "desk");
        front.addObjectOfInterest("table", "desk");
        front.addObjectOfInterest("worktable", "desk");
    }

    // EFFECTS: returns the next line of the current scene, or throws an exception if the scene has ended.
    public String getCurrentNextLine() throws SceneEndingException {
        return CURRENT_SCENE.getNextLine();
    }

    // MODIFIES: this
    // EFFECTS: current scene is the one that comes after the previous one,
    //          OR throws exception if it does not exist.
    public void switchToNextScene() throws NoNextSceneException {
        setCurrentScene(CURRENT_SCENE.getNextScene());
    }

    // TODO: implement according to plan
    public void executeAction(String[] keywords) throws InvalidActionException {

    }
}
