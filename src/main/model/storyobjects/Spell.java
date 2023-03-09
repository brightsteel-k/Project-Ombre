package model.storyobjects;

import java.util.Objects;

// TODO: Create more diverse spell system
// Arcane action that can be cast at key points in the story or during combat to accomplish something
public class Spell {
    private String incantation;
    private float damage;

    public String getIncantation() {
        return incantation;
    }

    public float getDamage() {
        return damage;
    }

    // EFFECTS: returns true iff this and the given object are identical Spells
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Spell spell = (Spell) o;
        return Float.compare(spell.damage, damage) == 0 && Objects.equals(incantation, spell.incantation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incantation, damage);
    }
}
