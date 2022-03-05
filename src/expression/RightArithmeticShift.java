package expression;

import java.math.BigDecimal;

public class RightArithmeticShift extends RightBinaryOperation {
    public RightArithmeticShift(GeneralExpression left, GeneralExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int a, int b) {
        return a >> b;
    }

    @Override
    protected BigDecimal calculate(BigDecimal a, BigDecimal b) {
        throw new IllegalArgumentException("Shifts on BigDecimals are unsupported");
    }

    @Override
    protected String getOperationSymbol() {
        return ">>";
    }

    @Override
    protected int getPriority() {
        return 5;
    }
}
