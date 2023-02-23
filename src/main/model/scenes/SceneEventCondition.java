package model.scenes;

// A condition that needs to be tested in order for a scene event to run. Key can be "@hasItem", "@hasSpell", or custom
public class SceneEventCondition {
    private String key;
    private String expected;

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
