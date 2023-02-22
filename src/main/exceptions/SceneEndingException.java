package exceptions;

import model.scenes.EndSceneEvent;

import java.util.List;

// Exception signalling that a scene has ended, and specifying what event should take place as a result
public class SceneEndingException extends Exception {
    private final List<EndSceneEvent> events;
    private final String lastMessage;

    // EFFECTS: SceneEndingException has the given event and no message.
    public SceneEndingException(List<EndSceneEvent> events) {
        this(null, events);
    }

    // EFFECTS: SceneEndingException has the given event and ending message.
    public SceneEndingException(String lastMessage, List<EndSceneEvent> events) {
        super();
        this.lastMessage = lastMessage;
        this.events = events;
    }

    public List<EndSceneEvent> getEvents() {
        return events;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
