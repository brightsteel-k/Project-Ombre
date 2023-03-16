package model.storyobjects;

import java.util.Objects;

// TODO: Create more diverse spell system
// Arcane action that can be cast at key points in the story or during combat to accomplish something
public class Spell {
    private String name;
    private String incantation;
    private String school;
    private String description;
    private float damage;

    public Spell(String name, String incantation, String school, String description, float damage) {
        this.name = name;
        this.incantation = incantation;
        this.school = school;
        this.description = description;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public String getIncantation() {
        return incantation;
    }

    public String getSchool() {
        return school;
    }

    public String getDescription() {
        return description;
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
        return Objects.equals(incantation, spell.incantation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incantation);
    }
}