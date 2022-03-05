package expression.exceptions.exception;

public class IncorrectPowException extends ExpressionException {
    public IncorrectPowException(String message) {
        super(message);
    }

    public IncorrectPowException(String message, Throwable cause) {
        super(message, cause);
    }
}
