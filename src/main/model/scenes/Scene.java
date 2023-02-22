package model.scenes;

import exceptions.InvalidSceneException;
import exceptions.SceneEndingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A distinct section of the story text with which the user will interact
public class Scene {

    private String[] texts;
    private List<EndSceneEvent> endSceneEvents;
    private int index;

    // EFFECTS: Scene has the given texts as its script, and its index set to 0.
    public Scene(String[] texts) {
        this.texts = texts;
        index = 0;
    }

    // EFFECTS: Scene has the given texts as its script, the given events as its actions to execute upon completion,
    //          and its index set to 0.
    public Scene(String[] texts, EndSceneEvent[] endSceneEvents) {
        this(texts);
        this.endSceneEvents = new ArrayList<>();
        this.endSceneEvents.addAll(Arrays.asList(endSceneEvents));
    }


    // REQUIRES: index <= texts.length
    // MODIFIES: this
    // EFFECTS: returns the next line in this scene's script and increments its index by 1,
    //          OR throws an exception if the scene has ended.
    public String getNextLine() throws SceneEndingException {
        if (index == texts.length) {
            throw getSceneEnd();
        }
        return texts[index++];
    }

    // EFFECTS: returns the event that should occur at the end of the scene.
    private SceneEndingException getSceneEnd() {
        return new SceneEndingException(endSceneEvents);
    }
}
