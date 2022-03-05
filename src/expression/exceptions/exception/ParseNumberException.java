package expression.exceptions.exception;

public class ParseNumberException extends ParseException {
    public ParseNumberException(String message) {
        super(message);
    }

    public ParseNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
