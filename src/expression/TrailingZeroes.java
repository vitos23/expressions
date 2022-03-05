package expression;

import java.math.BigDecimal;

public class TrailingZeroes extends UnaryOperation {
    public TrailingZeroes(GeneralExpression expression) {
        super(expression);
    }

    @Override
    protected int calculate(int a) {
        return Integer.numberOfTrailingZeros(a);
    }

    @Override
    protected BigDecimal calculate(BigDecimal a) {
        throw new IllegalArgumentException("t0 on BigDecimals is unsupported");
    }

    @Override
    protected String getOperationSymbol() {
        return "t0";
    }
}
