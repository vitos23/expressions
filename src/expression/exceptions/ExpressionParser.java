package expression.exceptions;

import expression.*;
import expression.exceptions.exception.MissingArgumentException;
import expression.exceptions.exception.ParseException;
import expression.exceptions.exception.ParseNumberException;
import expression.exceptions.exception.WrongParenthesisException;
import expression.exceptions.operations.*;

public class ExpressionParser implements Parser {
    @Override
    public TripleExpression parse(String expression) throws ParseException {
        return new ExpressionParserHelper(new StringCharSource(expression)).parse();
    }

    private static class ExpressionParserHelper extends BaseParser {
        private enum BinaryOperation {
            ADD("+", 10),
            SUB("-", 10),
            MUL("*", 20),
            DIV("/", 20),
            LS("<<", 5),
            RAS(">>", 5),
            RS(">>>", 5),
            POW("**", 30),
            LOG("//", 30);

            private final String symbol;
            private final int priority;

            BinaryOperation(String symbol, int priority) {
                this.symbol = symbol;
                this.priority = priority;
            }

            public String getSymbol() {
                return symbol;
            }

            public int getPriority() {
                return priority;
            }
        }

        private enum UnaryOperation {
            ABS("abs");

            private final String symbol;

            UnaryOperation(String symbol) {
                this.symbol = symbol;
            }

            public String getSymbol() {
                return symbol;
            }
        }

        private BinaryOperation cachedBinOp = null;

        public ExpressionParserHelper(CharSource source) {
            super(source);
        }

        private GeneralExpression constructUnaryOperation(UnaryOperation op, GeneralExpression e) {
            switch (op) {
                case ABS:
                    return new CheckedAbs(e);
                default:
                    throw new AssertionError("Invalid unary operation: " + op);
            }
        }

        public GeneralExpression parse() throws ParseException {
            GeneralExpression result = parseExpression(-1);
            if (!end()) {
                if (test('(') || test(')')) {
                    throw new WrongParenthesisException(getErrorMessage("Expected end of string"));
                }
                throw error("expected end of string");
            }
            return result;
        }

        private GeneralExpression parseExpression(int minPriority) throws ParseException {
            GeneralExpression result = parseElement();
            while (true) {
                BinaryOperation operation = getOperationSymbol(minPriority);
                if (operation == null) {
                    break;
                }
                result = constructBinaryOperation(
                        result,
                        operation,
                        parseExpression(operation.getPriority() + 1)
                );
            }
            return result;
        }

        private BinaryOperation getOperationSymbol(int minPriority) throws ParseException {
            parseOperationSymbol();
            if (cachedBinOp != null && cachedBinOp.getPriority() >= minPriority) {
                BinaryOperation res = cachedBinOp;
                cachedBinOp = null;
                return res;
            }
            return null;
        }

        private void parseOperationSymbol() throws ParseException {
            if (cachedBinOp != null) {
                return;
            }
            skipWhitespaces();
            if (take('>')) {
                expect('>');
                if (take('>')) {
                    cachedBinOp = BinaryOperation.RS;
                } else {
                    cachedBinOp = BinaryOperation.RAS;
                }
                return;
            }
            if (take('*')) {
                if (take('*')) {
                    cachedBinOp = BinaryOperation.POW;
                } else {
                    cachedBinOp = BinaryOperation.MUL;
                }
                return;
            }
            if (take('/')) {
                if (take('/')) {
                    cachedBinOp = BinaryOperation.LOG;
                } else {
                    cachedBinOp = BinaryOperation.DIV;
                }
                return;
            }
            for (BinaryOperation op : BinaryOperation.values()) {
                if (take(op.getSymbol().charAt(0))) {
                    expect(op.getSymbol().substring(1));
                    cachedBinOp = op;
                    return;
                }
            }
        }

        private GeneralExpression constructBinaryOperation(
                GeneralExpression left,
                BinaryOperation operation,
                GeneralExpression right
        ) {
            switch (operation) {
                case ADD:
                    return new CheckedAdd(left, right);
                case SUB:
                    return new CheckedSubtract(left, right);
                case MUL:
                    return new CheckedMultiply(left, right);
                case DIV:
                    return new CheckedDivide(left, right);
                case LS:
                    return new LeftShift(left, right);
                case RAS:
                    return new RightArithmeticShift(left, right);
                case RS:
                    return new RightShift(left, right);
                case LOG:
                    return new CheckedLog(left, right);
                case POW:
                    return new CheckedPow(left, right);
                default:
                    throw new AssertionError("Invalid binary operation");
            }
        }

        private GeneralExpression parseElement() throws ParseException {
            skipWhitespaces();
            GeneralExpression result = parseArgument();
            skipWhitespaces();
            return result;
        }

        private void expectEndOfToken() throws ParseException {
            expect(ch -> !Character.isDigit(ch) && !Character.isAlphabetic(ch), "end of token");
        }

        private GeneralExpression parseArgument() throws ParseException {
            if (take('-')) {
                return parseUnaryMinus();
            }
            if (take('(')) {
                return parseTerm();
            }
            if (between('0', '9')) {
                return parseNumber(false);
            }
            for (UnaryOperation op : UnaryOperation.values()) {
                if (take(op.getSymbol().charAt(0))) {
                    expect(op.getSymbol().substring(1));
                    expectEndOfToken();
                    return constructUnaryOperation(op, parseElement());
                }
            }
            if (between('x', 'z')) {
                char varName = take();
                expectEndOfToken();
                return new Variable(String.valueOf(varName));
            }
            throw new MissingArgumentException(getErrorMessage("argument expected"));
        }

        private GeneralExpression parseUnaryMinus() throws ParseException {
            if (between('0', '9')) {
                return parseNumber(true);
            }
            return new CheckedNegate(parseElement());
        }

        private GeneralExpression parseTerm() throws ParseException {
            GeneralExpression result = parseExpression(-1);
            if (!take(')')) {
                throw new WrongParenthesisException(getErrorMessage("Expected ')'"));
            }
            return result;
        }

        private GeneralExpression parseNumber(boolean negative) throws ParseException {
            StringBuilder sb = new StringBuilder();
            if (negative) {
                sb.append('-');
            }
            while (between('0', '9')) {
                sb.append(take());
            }
            int res;
            try {
                res = Integer.parseInt(sb.toString());
            } catch (NumberFormatException e) {
                throw new ParseNumberException(e.getMessage());
            }
            return new Const(res);
        }
    }
}
