package expression.exceptions;

import expression.exceptions.exception.IncorrectInputException;


public interface CharSource {
    boolean hasNext();
    char next();

    IncorrectInputException error(final String message);
    String getErrorMessage(String message);
}