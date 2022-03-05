package expression.parser;

import expression.*;

import java.util.Map;

public class ExpressionParser implements Parser {
    @Override
    public TripleExpression parse(String expression) {
        return new ExpressionParserHelper(new StringCharSource(expression)).parse();
    }

    private static class ExpressionParserHelper extends BaseParser {
        private enum BinaryOperation {
            ADD, SUB, MUL, DIV, LS, RAS, RS
        }

        private static final Map<BinaryOperation, Integer> priorities = Map.of(
                BinaryOperation.ADD, 10,
                BinaryOperation.SUB, 10,
                BinaryOperation.MUL, 20,
                BinaryOperation.DIV, 20,
                BinaryOperation.LS, 5,
                BinaryOperation.RAS, 5,
                BinaryOperation.RS, 5
        );

        private static final Map<BinaryOperation, String> operationSymbols = Map.of(
                BinaryOperation.ADD, "+",
                BinaryOperation.SUB, "-",
                BinaryOperation.MUL, "*",
                BinaryOperation.DIV, "/",
                BinaryOperation.LS, "<<",
                BinaryOperation.RAS, ">>",
                BinaryOperation.RS, ">>>"
        );

        private enum UnaryOperation {
            L0, T0
        }

        private static final Map<UnaryOperation, String> unarySymbols = Map.of(
                UnaryOperation.L0, "l0",
                UnaryOperation.T0, "t0"
        );

        private GeneralExpression constructUnaryOperation(UnaryOperation op, GeneralExpression e) {
            switch (op) {
                case L0:
                    return new LeadingZeroes(e);
                case T0:
                    return new TrailingZeroes(e);
                default:
                    throw new AssertionError("Invalid unary operation: " + op);
            }
        }

        public ExpressionParserHelper(CharSource source) {
            super(source);
        }

        public GeneralExpression parse() {
            final GeneralExpression result = parseExpression(-1);
            if (!end()) {
                throw error("expected end of string");
            }
            return result;
        }

        private GeneralExpression parseExpression(int minPriority) {
            GeneralExpression result = parseElement();
            while (true) {
                final BinaryOperation operation = parseOperationSymbol(minPriority);
                if (operation == null) {
                    return  result;
                }
                result = constructBinaryOperation(
                        result,
                        operation,
                        parseExpression(priorities.get(operation) + 1)
                );
            }
        }

        private BinaryOperation parseOperationSymbol(final int minPriority) {
            skipWhitespaces();
            if (priorities.get(BinaryOperation.RAS) >= minPriority && take('>')) {
                expect('>');
                if (take('>')) {
                    return BinaryOperation.RS;
                } else {
                    return BinaryOperation.RAS;
                }
            }
            for (BinaryOperation op : BinaryOperation.values()) {
                if (priorities.get(op) >= minPriority && take(operationSymbols.get(op).charAt(0))) {
                    expect(operationSymbols.get(op).substring(1));
                    return op;
                }
            }
            return null;
        }

        private GeneralExpression constructBinaryOperation(
                GeneralExpression left,
                BinaryOperation operation,
                GeneralExpression right
        ) {
            switch (operation) {
                case ADD:
                    return new Add(left, right);
                case SUB:
                    return new Subtract(left, right);
                case MUL:
                    return new Multiply(left, right);
                case DIV:
                    return new Divide(left, right);
                case LS:
                    return new LeftShift(left, right);
                case RAS:
                    return new RightArithmeticShift(left, right);
                case RS:
                    return new RightShift(left, right);
                default:
                    throw new AssertionError("Invalid binary operation");
            }
        }

        private GeneralExpression parseElement() {
            skipWhitespaces();
            GeneralExpression result = parseToken();
            skipWhitespaces();
            return result;
        }

        private void expectEndOfToken() {
            expect(ch -> !Character.isDigit(ch) && !Character.isAlphabetic(ch), "end of token");
        }

        private GeneralExpression parseToken() {
            if (take('-')) {
                return parseUnaryMinus();
            }
            if (take('(')) {
                return parseTerm();
            }
            for (UnaryOperation op : UnaryOperation.values()) {
                if (take(unarySymbols.get(op).charAt(0))) {
                    expect(unarySymbols.get(op).substring(1));
                    expectEndOfToken();
                    return constructUnaryOperation(op, parseElement());
                }
            }
            if (between('0', '9')) {
                return parseNumber(false);
            }
            if (between('x', 'z')) {
                char varName = take();
                expectEndOfToken();
                return new Variable(String.valueOf(varName));
            }
            throw error("value expected");
        }

        private GeneralExpression parseUnaryMinus() {
            if (between('0', '9')) {
                return parseNumber(true);
            }
            return new UnaryMinus(parseElement());
        }

        private GeneralExpression parseTerm() {
            GeneralExpression result = parseExpression(-1);
            expect(')');
            return result;
        }

        private GeneralExpression parseNumber(boolean negative) {
            StringBuilder sb = new StringBuilder();
            if (negative) {
                sb.append('-');
            }
            while (between('0', '9')) {
                sb.append(take());
            }
            return new Const(Integer.parseInt(sb.toString()));
        }
    }
}
