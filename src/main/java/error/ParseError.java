package error;

public class ParseError extends RuntimeException {

    public ParseError(String message) {
        super(message);
    }

    public ParseError(ParseError cause, int lineNumber) {
        super("error near line " + lineNumber + ": " + cause.getMessage(), cause);
    }
}