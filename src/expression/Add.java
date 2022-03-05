package expression;

import java.math.BigDecimal;

public class Add extends BinaryOperation {
    public Add(GeneralExpression left, GeneralExpression right) {
        super(left, right);
    }

    @Override
    protected String getOperationSymbol() {
        return "+";
    }

    @Override
    protected int getPriority() {
        return 10;
    }

    @Override
    protected int calculate(int a, int b) {
        return a + b;
    }

    @Override
    protected BigDecimal calculate(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }
}
