package expression;

import java.math.BigDecimal;

public class Divide extends BinaryOperation {
    public Divide(GeneralExpression left, GeneralExpression right) {
        super(left, right);
    }

    @Override
    protected String getOperationSymbol() {
        return "/";
    }

    @Override
    protected int getPriority() {
        return 20;
    }

    @Override
    protected int calculate(int a, int b) {
        return a / b;
    }

    @Override
    protected BigDecimal calculate(BigDecimal a, BigDecimal b) {
        return a.divide(b);
    }

    @Override
    protected boolean rightNeedBrackets() {
        return right instanceof BinaryOperation && ((BinaryOperation) right).getPriority() == getPriority();
    }
}
