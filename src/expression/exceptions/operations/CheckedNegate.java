package expression.exceptions.operations;

import expression.GeneralExpression;
import expression.UnaryMinus;
import expression.exceptions.exception.OverflowException;

public class CheckedNegate extends UnaryMinus {
    public CheckedNegate(GeneralExpression expression) {
        super(expression);
    }

    @Override
    protected int calculate(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException(
                    String.format("overflow (calculate negate) for '%d'", a)
            );
        }
        return -a;
    }
}
