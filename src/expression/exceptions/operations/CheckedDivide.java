package expression.exceptions.operations;

import expression.Divide;
import expression.GeneralExpression;
import expression.exceptions.exception.DivisionByZeroException;
import expression.exceptions.exception.OverflowException;

public class CheckedDivide extends Divide {
    public CheckedDivide(GeneralExpression left, GeneralExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int a, int b) {
        if (b == 0) {
            throw new DivisionByZeroException(
                    String.format("division by zero for '%d / %d'", a, b)
            );
        }
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException(
                    String.format("overflow (calculate division) for '%d / %d'", a, b)
            );
        }
        return a / b;
    }
}
