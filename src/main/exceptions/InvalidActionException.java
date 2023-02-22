package exceptions;

public class InvalidActionException extends Exception {

    String invalidObject;

    public InvalidActionException() {
        this(null);
    }

    public InvalidActionException(String obj) {
        super();
        invalidObject = obj;
    }

    public boolean invalidObject() {
        return invalidObject != null;
    }

    public String getInvalidObject() {
        return invalidObject;
    }
}
