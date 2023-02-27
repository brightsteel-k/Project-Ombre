package exceptions;

// Exception signifying that the player's input could not be parsed into a valid action, due to the use of a
// context-dependent ambiguous pronoun like "it", "them", "those", or "him".
public class AmbiguousActionException extends InvalidActionException {
    
}
