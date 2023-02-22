package model.scenes;

public class EndSceneEvent {
    private EndSceneEventType type;
    private String keyword;

    public EndSceneEvent(EndSceneEventType type) {
        this(type, null);
    }

    public EndSceneEvent(EndSceneEventType type, String keyword) {
        this.type = type;
        this.keyword = keyword;
    }

    public boolean isType(EndSceneEventType typeIn) {
        return type == typeIn;
    }

    public EndSceneEventType getType() {
        return type;
    }

    public String getKeyword() {
        return keyword;
    }
}
