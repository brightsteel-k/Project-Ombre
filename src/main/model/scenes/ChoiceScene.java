package model.scenes;

import exceptions.SceneEndingException;

// A Scene that ends by presenting the user with a choice and calling the next scene based on their decision
public class ChoiceScene extends Scene {

    // EFFECTS: Scene has the given texts as its script, and its index set to 0.
    public ChoiceScene(String[] textsIn) {
        super(textsIn);
    }

    @Override
    protected SceneEndingException getEndSceneEvent() {
        return new SceneEndingException(EndSceneEvent.FREE_ROAM);
    }
}
