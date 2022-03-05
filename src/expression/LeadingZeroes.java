package expression;

import java.math.BigDecimal;

public class LeadingZeroes extends UnaryOperation {
    public LeadingZeroes(GeneralExpression expression) {
        super(expression);
    }

    @Override
    protected int calculate(int a) {
        return Integer.numberOfLeadingZeros(a);
    }

    @Override
    protected BigDecimal calculate(BigDecimal a) {
        throw new IllegalArgumentException("l0 on BigDecimals is unsupported");
    }

    @Override
    protected String getOperationSymbol() {
        return "l0";
    }
}
