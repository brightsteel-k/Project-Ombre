package exceptions;

// Exception signifying that the player's input could not be parsed into a valid action for the context
public class InvalidActionException extends Exception {

    private String invalidObject;
    private int type;

    public InvalidActionException(int type) {
        this(type, null);
    }

    // InvalidActionException has given message and type; 1 is an invalid object, 2 is an ambiguous pronoun
    public InvalidActionException(int type, String invalidObject) {
        super();
        this.type = type;
        this.invalidObject = invalidObject;
    }

    // EFFECTS: returns the type; 0 is invalid action, 1 is invalid object, 2 is ambiguous pronoun
    public int getType() {
        return type;
    }

    public String getInvalidObject() {
        return invalidObject;
    }
}
