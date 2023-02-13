package exceptions;

import model.scenes.EndSceneEvent;

// Exception signalling that a scene has ended, and specifying what event should take place as a result
public class SceneEndingException extends Exception {
    private final EndSceneEvent event;

    // EFFECTS: SceneEndingException has the given event.
    public SceneEndingException(EndSceneEvent e) {
        super();
        event = e;
    }

    public EndSceneEvent getEvent() {
        return event;
    }
}
