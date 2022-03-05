package expression.exceptions.operations;

import expression.GeneralExpression;
import expression.RightBinaryOperation;
import expression.exceptions.exception.IncorrectLogException;
import expression.exceptions.Utils;

import java.math.BigDecimal;

public class CheckedLog extends RightBinaryOperation {
    public CheckedLog(GeneralExpression left, GeneralExpression right) {
        super(left, right);
    }

    @Override
    protected int calculate(int a, int b) {
        if (a <= 0 || b <= 0 || b == 1) {
            throw new IncorrectLogException(String.format("Log[%d, %d] is undefined!", b, a));
        }
        int res = 0;
        int cur = 1;
        while (cur <= a) {
            res++;
            if (Utils.checkOverflow(cur, b)) {
                break;
            }
            cur *= b;
        }
        return res - 1;
    }

    @Override
    protected BigDecimal calculate(BigDecimal a, BigDecimal b) {
        throw new IllegalArgumentException("Logs on BigDecimals are unsupported");
    }

    @Override
    protected String getOperationSymbol() {
        return "//";
    }

    @Override
    protected int getPriority() {
        return 30;
    }
}
