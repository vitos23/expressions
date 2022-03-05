package expression.exceptions.operations;

import expression.GeneralExpression;
import expression.UnaryOperation;
import expression.exceptions.exception.OverflowException;

import java.math.BigDecimal;

public class CheckedAbs extends UnaryOperation {
    public CheckedAbs(GeneralExpression expression) {
        super(expression);
    }

    @Override
    protected int calculate(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException(
                    String.format("overflow (calculate abs) for '%d'", a)
            );
        }
        return a >= 0 ? a : -a;
    }

    @Override
    protected BigDecimal calculate(BigDecimal a) {
        return a.abs();
    }

    @Override
    protected String getOperationSymbol() {
        return "abs";
    }
}
