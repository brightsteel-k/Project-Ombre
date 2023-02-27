package exceptions;

// Exception signifying that the player's input could not be parsed into a valid action, due to the referral of
// an object that is unreachable in the current context.
public class ActionSubjectException extends InvalidActionException {

    private String invalidObject;

    // EFFECTS: ActionSubjectException logs that it was caused by the given invalid object
    public ActionSubjectException(String invalidObject) {
        this.invalidObject = invalidObject;
    }

    // EFFECTS: returns the name of the invalid object that triggered this exception
    public String getInvalidObject() {
        return invalidObject;
    }
}
