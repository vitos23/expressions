package expression.exceptions.exception;

public class MissingArgumentException extends ParseException {
    public MissingArgumentException(String message) {
        super(message);
    }

    public MissingArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
