package model.storyobjects;

// TODO: Create more diverse spell system
// Arcane action that can be cast at key points in the story or during combat to accomplish something
public class Spell {
    private String keyword;
    private float damage;

    public String getKeyword() {
        return keyword;
    }

    public float getDamage() {
        return damage;
    }
}
