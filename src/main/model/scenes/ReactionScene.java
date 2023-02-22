package model.scenes;

import exceptions.SceneEndingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A Scene that ends by placing the user into free roam exploration mode
public class ReactionScene extends Scene {

    private List<EndSceneEvent> endSceneEvents;

    // EFFECTS: Scene has the given texts as its script, and its index set to 0.
    public ReactionScene(String[] texts) {
        super(texts);
        endSceneEvents = new ArrayList<>();
    }

    // EFFECTS: Scene has the given texts as its script, given events to execute when it ends, and its index set to 0.
    public ReactionScene(String[] texts, EndSceneEvent[] endSceneEvents) {
        super(texts);
        this.endSceneEvents = new ArrayList<>();
        this.endSceneEvents.addAll(Arrays.asList(endSceneEvents));
    }

    // REQUIRES: index < texts.length
    // MODIFIES: this
    // EFFECTS: returns the next line in this scene's script and increments its index by 1,
    //          OR throws an exception if the scene is about to end.
    @Override
    public String getNextLine() throws SceneEndingException {
        if (index == texts.length - 1) {
            throw getEndSceneEvent();
        }
        return texts[index++];
    }

    @Override
    protected SceneEndingException getEndSceneEvent() {
        return new SceneEndingException(texts[index], endSceneEvents);
    }
}
