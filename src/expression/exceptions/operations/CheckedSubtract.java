package expression.exceptions.operations;

import expression.GeneralExpression;
import expression.Subtract;
import expression.exceptions.exception.OverflowException;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(GeneralExpression left, GeneralExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int a, int b) {
        int res = a - b;
        if (a >= 0 && b < 0 && res <= 0 || a < 0 && b > 0 && res >= 0) {
            throw new OverflowException(
                    String.format("overflow (calculate subtract) for '%d - %d'", a, b)
            );
        }
        return res;
    }
}
