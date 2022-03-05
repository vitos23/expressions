package expression.exceptions.exception;

public class WrongParenthesisException extends ParseException {
    public WrongParenthesisException(String message) {
        super(message);
    }

    public WrongParenthesisException(String message, Throwable cause) {
        super(message, cause);
    }
}
