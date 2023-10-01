package error;

public class ParseException extends RuntimeException {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(ParseException cause, int lineNumber) {
        super("error near line " + lineNumber + ": " + cause.getMessage(), cause);
    }
}
