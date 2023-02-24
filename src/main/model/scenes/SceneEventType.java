package model.scenes;

import com.google.gson.annotations.SerializedName;

// Different types of events that might occur during the story
public enum SceneEventType {
    @SerializedName("start_exploring")
    START_EXPLORING,
    @SerializedName("next_scene")
    NEXT_SCENE,
    @SerializedName("display_text")
    DISPLAY_TEXT,
    @SerializedName("change_location")
    CHANGE_LOCATION,
    @SerializedName("acquire_item")
    ACQUIRE_ITEM,
    @SerializedName("learn_spell")
    LEARN_SPELL,
    @SerializedName("set_condition")
    SET_CONDITION
}
