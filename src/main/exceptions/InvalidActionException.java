package exceptions;

// Exception signifying that the player's input could not be parsed into a valid action for the context
public class InvalidActionException extends Exception {

    private String invalidObject;
    private int type;

    // EFFECTS: InvalidActionException has given type and no message; type=0 is an invalid action, 1 is an invalid
    //          object, 2 is an ambiguous pronoun
    public InvalidActionException(int type) {
        super();
        this.type = type;
    }

    // EFFECTS: InvalidActionException has given message and type=1; an invalid object
    public InvalidActionException(String invalidObject) {
        this(1);
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
