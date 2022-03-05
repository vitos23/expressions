package expression;

import java.math.BigDecimal;

public abstract class UnaryOperation implements GeneralExpression {
    protected final GeneralExpression expression;

    public UnaryOperation(GeneralExpression expression) {
        this.expression = expression;
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return calculate(expression.evaluate(x));
    }

    @Override
    public int evaluate(int x) {
        return calculate(expression.evaluate(x));
    }

    abstract protected int calculate(int a);

    abstract protected BigDecimal calculate(BigDecimal a);

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(expression.evaluate(x, y, z));
    }

    abstract protected String getOperationSymbol();

    @Override
    public String toString() {
        return getOperationSymbol() + "(" + expression + ")";
    }

    @Override
    public String toMiniString() {
        if (expression instanceof BinaryOperation) {
            return getOperationSymbol() + "(" + expression.toMiniString() + ")";
        }
        return getOperationSymbol() + " " + expression.toMiniString();
    }
}
