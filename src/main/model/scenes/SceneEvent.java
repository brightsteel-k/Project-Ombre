package model.scenes;

// An event that can be triggered during the game in response to the end of a scene or player interaction.
// Some possible effects include starting a new scene, displaying a message, or teaching the player a spell.
public class SceneEvent {
    private SceneEventType type;
    private String keyword;
    private SceneEventCondition condition;


    public SceneEvent(SceneEventType type) {
        this(type, null, null);
    }

    public SceneEvent(SceneEventType type, String keyword) {
        this(type, keyword, null);
    }

    public SceneEvent(SceneEventType type, SceneEventCondition condition) {
        this(type, null, condition);
    }

    public SceneEvent(SceneEventType type, String keyword, SceneEventCondition condition) {
        this.type = type;
        this.keyword = keyword;
        this.condition = condition;
    }

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

    public boolean hasCondition() {
        return condition != null;
    }

    public SceneEventCondition getCondition() {
        return condition;
    }
}
