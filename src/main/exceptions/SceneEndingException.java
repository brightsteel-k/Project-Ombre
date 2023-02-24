package exceptions;

import model.storyobjects.SceneEvent;

import java.util.List;

// Exception signifying that a scene has ended, and specifying what events should take place as a result
public class SceneEndingException extends Exception {
    private final List<SceneEvent> events;
    private boolean startExploring;
    private String nextScene;

    // EFFECTS: SceneEndingException is attached to the given events and post-scene behaviour
    public SceneEndingException(List<SceneEvent> events, boolean startExploring, String nextScene) {
        this.events = events;
        this.startExploring = startExploring;
        this.nextScene = nextScene;
    }

    public List<SceneEvent> getEvents() {
        return events;
    }

    public boolean shouldStartExploring() {
        return startExploring;
    }

    public String getNextScene() {
        return nextScene;
    }
}
