package model.storyobjects;

// A condition that needs to be tested in order for a scene event to run.
public class SceneEventCondition {
    private String key;
    private String expected;

    // SceneEventCondition expects the value of the condition with given key name to match the given value.
    // Keys can also be codes "@hasItem", "@hasSpell", "@missingItem", or "@missingSpell", to depend on player's stats.
    public SceneEventCondition(String key, String expected) {
        this.key = key;
        this.expected = expected;
    }

    public String getKey() {
        return key;
    }

    public String getExpected() {
        return expected;
    }

    // EFFECTS: returns true iff this and the given object are identical SceneEventConditions
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != SceneEventCondition.class) {
            return false;
        }
        SceneEventCondition other = (SceneEventCondition)obj;
        return key.equals(other.key) && expected.equals(other.expected);
    }
}
