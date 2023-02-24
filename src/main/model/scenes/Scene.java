package model.scenes;

import exceptions.SceneEndingException;
import util.Exclude;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A distinct section of the story text with which the user will interact
public class Scene {

    private String[] texts;
    private List<SceneEvent> endSceneEvents;
    @Exclude
    private int index;

    // EFFECTS: Scene has the given texts as its script, and its index set to 0
    public Scene(String[] texts) {
        this.texts = texts;
        index = 0;
    }

    // EFFECTS: Scene has the given texts as its script, the given events as its actions to execute upon completion,
    //          and its index set to 0.
    public Scene(String[] texts, SceneEvent[] endSceneEvents) {
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

    // EFFECTS: returns the event that should occur at the end of the scene
    private SceneEndingException getSceneEnd() {
        return new SceneEndingException(endSceneEvents, shouldStartExploring(), nextScene());
    }

    // REQUIRES: if a SceneEvent signalling the start of exploration is in endSceneEvents, it's in the final
    //           position.
    // EFFECTS: returns true iff exploration should start at the end of this scene
    private boolean shouldStartExploring() {
        return endSceneEvents.get(endSceneEvents.size() - 1).isType(SceneEventType.START_EXPLORING);
    }

    // REQUIRES: if a SceneEvent signalling the transition to the next scene is in endSceneEvents, it's in the final
    //           position.
    // EFFECTS: returns the next scene's id, or null if there is none
    private String nextScene() {
        SceneEvent lastEvent = endSceneEvents.get(endSceneEvents.size() - 1);
        return lastEvent.isType(SceneEventType.NEXT_SCENE) ? lastEvent.getKeyword() : null;
    }
}
