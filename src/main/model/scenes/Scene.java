package model.scenes;

import exceptions.NoNextSceneException;
import exceptions.SceneEndingException;
import ui.Printer;

// Framework for a scene: a distinct section of the story text with which the user will interact
public abstract class Scene {

    protected String[] texts;
    protected int index;

    // EFFECTS: Scene has the given texts as its script, and its index set to 0.
    public Scene(String[] textsIn) {
        texts = textsIn;
        index = 0;
    }

    // REQUIRES: index < texts.length
    // MODIFIES: this
    // EFFECTS: returns the next line in this scene's script and increments its index by 1,
    //          OR throws an exception if the scene has ended.
    public String getNextLine() throws SceneEndingException {
        if (index == texts.length) {
            throw getEndSceneEvent();
        }
        return texts[index++];
    }

    // EFFECTS: returns the event that should occur at the end of the scene.
    protected abstract SceneEndingException getEndSceneEvent();

    // EFFECTS: returns the id of the scene that directly follows this one, or the empty string if no scene does.
    public String getNextScene() throws NoNextSceneException {
        throw new NoNextSceneException();
    }
}
