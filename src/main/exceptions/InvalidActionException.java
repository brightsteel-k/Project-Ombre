package exceptions;

// Exception signifying that the player's input could not be parsed into a valid action for the context
public class InvalidActionException extends Exception {

    private String invalidObject;

    public InvalidActionException() {
        this(null);
    }

    public InvalidActionException(String obj) {
        super();
        invalidObject = obj;
    }

    // EFFECTS: returns true iff this was triggered by an invalid object
    public boolean invalidObject() {
        return invalidObject != null;
    }

    public String getInvalidObject() {
        return invalidObject;
    }
}
