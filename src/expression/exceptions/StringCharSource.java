package expression.exceptions;

import expression.exceptions.exception.IncorrectInputException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class StringCharSource implements CharSource {
    private final String string;
    private int pos;

    public StringCharSource(String string) {
        this.string = string;
    }

    @Override
    public boolean hasNext() {
        return pos < string.length();
    }

    @Override
    public char next() {
        return string.charAt(pos++);
    }

    @Override
    public IncorrectInputException error(String message) {
        return new IncorrectInputException(getErrorMessage(message));
    }

    @Override
    public String getErrorMessage(String message) {
        return String.format(
                "%d: %s (\"%s\")", pos, message,
                string.substring(Math.max(pos - 5, 0), Math.min(pos + 5, string.length()))
        );
    }
}