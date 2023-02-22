package model.scenes;

import exceptions.SceneEndingException;
import model.StoryController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A Scene that presents the user with multiple blocks of text
public class DescriptiveScene extends Scene {
    protected String nextScene;

    // EFFECTS: this has the given text as its script, index set to 0. When it ends, it will signal for
    //          the scene with id nextIn to begin.
    public DescriptiveScene(String text, String nextIn) {
        super(new String[] { text });
        nextScene = nextIn;
    }

    // EFFECTS: this has the given texts as its script and index set to 0. When it ends, it will signal for
    //          the scene with id nextIn to begin.
    public DescriptiveScene(String[] texts, String nextIn) {
        super(texts);
        nextScene = nextIn;
    }

    // EFFECTS: signals that the end of this scene should lead to the beginning of the next.
    @Override
    protected SceneEndingException getEndSceneEvent() {
        return new SceneEndingException(new ArrayList<>(Arrays.asList(new EndSceneEvent[] {
                new EndSceneEvent(EndSceneEventType.NEXT_SCENE, nextScene)
        })));
    }
}
