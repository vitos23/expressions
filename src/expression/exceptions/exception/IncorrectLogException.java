package expression.exceptions.exception;

public class IncorrectLogException extends ExpressionException {
    public IncorrectLogException(String message) {
        super(message);
    }

    public IncorrectLogException(String message, Throwable cause) {
        super(message, cause);
    }
}
