package expression.exceptions.exception;

public class OverflowException extends ExpressionException {
    public OverflowException(String message) {
        super(message);
    }

    public OverflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
