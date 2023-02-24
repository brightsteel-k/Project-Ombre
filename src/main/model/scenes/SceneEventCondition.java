package model.scenes;

// A condition that needs to be tested in order for a scene event to run.
public class SceneEventCondition {
    private String key;
    private String expected;

    // SceneEventCondition expects the value of the condition with given key name to match the given value.
    // Keys can also be codes "@hasItem", "@hasSpell", "@missingItem", or "@missingSpell", to depend on Player stats.
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
}
