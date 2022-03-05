package expression;

import java.math.BigDecimal;

public class UnaryMinus extends UnaryOperation {
    public UnaryMinus(GeneralExpression expression) {
        super(expression);
    }

    @Override
    protected int calculate(int a) {
        return -a;
    }

    @Override
    protected BigDecimal calculate(BigDecimal a) {
        return a.negate();
    }

    @Override
    protected String getOperationSymbol() {
        return "-";
    }
}
