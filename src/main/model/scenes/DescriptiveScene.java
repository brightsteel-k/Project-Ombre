package model.scenes;

import exceptions.SceneEndingException;
import model.StoryController;

// A Scene that presents the user with multiple blocks of text
public class DescriptiveScene extends Scene {

    protected String nextScene;

    // EFFECTS: this has the given text as its script, index set to 0. When it ends, it will signal for
    //          the scene with id nextIn to begin.
    public DescriptiveScene(String textIn, String nextIn) {
        super(new String[] { textIn });
        nextScene = nextIn;
    }

    // EFFECTS: this has the given texts as its script and index set to 0. When it ends, it will signal for
    //          the scene with id nextIn to begin.
    public DescriptiveScene(String[] textsIn, String nextIn) {
        super(textsIn);
        nextScene = nextIn;
    }

    // EFFECTS: signals that the end of this scene should lead to the beginning of the next.
    @Override
    protected SceneEndingException getEndSceneEvent() {
        return new SceneEndingException(EndSceneEvent.NEXT_SCENE);
    }

    @Override
    public String getNextScene() {
        return nextScene;
    }
}
