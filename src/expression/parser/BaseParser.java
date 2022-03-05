package expression.parser;

import java.util.function.Predicate;

public class BaseParser {
    private static final char END = 0;

    private final CharSource source;
    private char ch;

    public BaseParser(CharSource source) {
        this.source = source;
        take();
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected void expect(final char expected) {
        if (!take(expected)) {
            throw error(String.format("Expected '%s', but found '%s'", expected, ch));
        }
    }

    protected void expect(final Predicate<Character> expected, final String errorExpectedMessage) {
        if (!expected.test(ch)) {
            throw error(String.format("Expected '%s', but found '%s'", errorExpectedMessage, ch));
        }
    }

    protected IllegalArgumentException error(String message) {
        return source.error(message);
    }

    protected void expect(final String expected) {
        for (char c : expected.toCharArray()) {
            expect(c);
        }
    }

    protected boolean end() {
        return test(END);
    }

    protected void skipWhitespaces() {
        while (Character.isWhitespace(ch)) {
            take();
        }
    }

    protected boolean between(final char min, final char max) {
        return min <= ch && ch <= max;
    }
}
