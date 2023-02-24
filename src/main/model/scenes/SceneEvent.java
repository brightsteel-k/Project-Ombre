package model.scenes;

// An event that can be triggered during the game in response to the end of a scene or player interaction.
// Some possible effects include starting a new scene, displaying a message, or teaching the player a spell.
public class SceneEvent {
    private SceneEventType type;
    private String keyword;
    private SceneEventCondition condition;

    // EFFECTS: returns true iff this SceneEvent is of the given SceneEventType
    public boolean isType(SceneEventType typeIn) {
        return type == typeIn;
    }

    public SceneEventType getType() {
        return type;
    }

    public String getKeyword() {
        return keyword;
    }

    // EFFECTS: returns true iff this SceneEvent has a non-null condition
    public boolean hasCondition() {
        return condition != null;
    }

    public SceneEventCondition getCondition() {
        return condition;
    }
}
