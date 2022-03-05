package expression.exceptions.operations;

import expression.Add;
import expression.GeneralExpression;
import expression.exceptions.exception.OverflowException;

public class CheckedAdd extends Add {
    public CheckedAdd(GeneralExpression left, GeneralExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int a, int b) {
        int res = a + b;
        if (a > 0 && b > 0 && res <= 0 || a < 0 && b < 0 && res >= 0) {
            throw new OverflowException(
                    String.format("overflow (calculate sum) for '%d + %d'", a, b)
            );
        }
        return res;
    }
}
