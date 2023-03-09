package model.storyobjects;

import exceptions.SceneEndingException;
import util.Exclude;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// A distinct section of the story text with which the user will interact
public class Scene {

    private String[] texts;
    private List<SceneEvent> endSceneEvents;
    @Exclude
    private int index;

    // MODIFIES: this
    // EFFECTS: prepares scene to play from the beginning
    public void startScene() {
        index = 0;
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
        return new SceneEndingException(endSceneEvents);
    }

    // EFFECTS: returns true iff this and the given object are identical Scenes
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Scene scene = (Scene) o;
        return Arrays.equals(texts, scene.texts) && Objects.equals(endSceneEvents, scene.endSceneEvents);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(endSceneEvents);
        result = 31 * result + Arrays.hashCode(texts);
        return result;
    }
}
