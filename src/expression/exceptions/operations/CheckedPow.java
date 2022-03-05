package expression.exceptions.operations;

import expression.GeneralExpression;
import expression.RightBinaryOperation;
import expression.exceptions.exception.IncorrectPowException;
import expression.exceptions.Utils;

import java.math.BigDecimal;

public class CheckedPow extends RightBinaryOperation {
    public CheckedPow(GeneralExpression left, GeneralExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int a, int b) {
        if (b < 0 || a == 0 && b == 0) {
            throw new IncorrectPowException(
                    String.format("overflow (calculate pow) for '%d ** %d'", a, b)
            );
        }
        if (a == 0) {
            return 0;
        }
        if (a == 1) {
            return 1;
        }
        if (a == -1) {
            return b % 2 == 0 ? 1 : -1;
        }
        int res = 1;
        for (int i = 0; i < b; i++) {
            res = Utils.checkedMultiply(res, a);
        }
        return res;
    }

    @Override
    protected BigDecimal calculate(BigDecimal a, BigDecimal b) {
        throw new IllegalArgumentException("Shifts on BigDecimals are unsupported");
    }

    @Override
    protected String getOperationSymbol() {
        return "**";
    }

    @Override
    protected int getPriority() {
        return 30;
    }
}
