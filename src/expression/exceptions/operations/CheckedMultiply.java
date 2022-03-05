package expression.exceptions.operations;

import expression.GeneralExpression;
import expression.Multiply;
import expression.exceptions.Utils;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(GeneralExpression left, GeneralExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int a, int b) {
        return Utils.checkedMultiply(a, b);
    }
}
