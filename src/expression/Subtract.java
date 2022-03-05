package expression;

import java.math.BigDecimal;

public class Subtract extends BinaryOperation {
    public Subtract(GeneralExpression left, GeneralExpression right) {
        super(left, right);
    }

    @Override
    protected String getOperationSymbol() {
        return "-";
    }

    @Override
    protected int getPriority() {
        return 10;
    }

    @Override
    protected int calculate(int a, int b) {
        return a - b;
    }

    @Override
    protected BigDecimal calculate(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    @Override
    protected boolean rightNeedBrackets() {
        return right instanceof BinaryOperation && ((BinaryOperation) right).getPriority() == getPriority();
    }
}
