package model.storyobjects;

import java.util.Arrays;
import java.util.Objects;

// An event that can be triggered during the game in response to the end of a scene or player interaction.
// Some possible effects include starting a new scene, displaying a message, or teaching the player a spell.
public class SceneEvent {
    private SceneEventType type;
    private String keyword;
    private SceneEventCondition[] conditions;

    // REQUIRES: type == SceneEventType.START_EXPLORING
    // EFFECTS: SceneEvent has the given type, no keyword, and no conditions
    public SceneEvent(SceneEventType type) {
        this(type, null, null);
    }

    // EFFECTS: SceneEvent has the given type and keyword, and no condition
    public SceneEvent(SceneEventType type, String keyword) {
        this(type, keyword, null);
    }

    // REQUIRES: type == SceneEventType.START_EXPLORING
    // EFFECTS: SceneEvent has the given type and conditions, but no keyword
    public SceneEvent(SceneEventType type, SceneEventCondition[] condition) {
        this(type, null, condition);
    }

    // EFFECTS: SceneEvent has the given type, keyword, and conditions
    public SceneEvent(SceneEventType type, String keyword, SceneEventCondition[] conditions) {
        this.type = type;
        this.keyword = keyword;
        this.conditions = conditions;
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

    // EFFECTS: returns true iff this SceneEvent contains a non-null set of condition
    public boolean hasConditions() {
        return conditions != null;
    }

    public SceneEventCondition[] getConditions() {
        return conditions;
    }

    // EFFECTS: returns true iff this and the given object are identical SceneEvents
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SceneEvent that = (SceneEvent) o;
        return type == that.type && Objects.equals(keyword, that.keyword) && Arrays.equals(conditions, that.conditions);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(type, keyword);
        result = 31 * result + Arrays.hashCode(conditions);
        return result;
    }
}
