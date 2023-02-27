package exceptions;

import model.storyobjects.SceneEvent;

import java.util.List;

// Exception signifying that a scene has ended, and specifying what events should take place as a result
public class SceneEndingException extends Exception {
    private final List<SceneEvent> events;

    // EFFECTS: SceneEndingException is attached to the given events and post-scene behaviour
    public SceneEndingException(List<SceneEvent> events) {
        this.events = events;
    }

    public List<SceneEvent> getEvents() {
        return events;
    }
}
