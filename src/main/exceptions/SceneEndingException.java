package exceptions;

import model.scenes.EndSceneEvent;

import java.util.List;

// Exception signalling that a scene has ended, and specifying what event should take place as a result
public class SceneEndingException extends Exception {
    private final List<EndSceneEvent> events;

    // EFFECTS: SceneEndingException has the given event.
    public SceneEndingException(List<EndSceneEvent> events) {
        this.events = events;
    }

    public List<EndSceneEvent> getEvents() {
        return events;
    }
}
