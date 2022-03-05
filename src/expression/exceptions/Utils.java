package expression.exceptions;

import expression.exceptions.exception.OverflowException;

public class Utils {
    public static boolean checkOverflow(int a, int b) {
        int res = a * b;
        return b != 0 && (a == -1 && b == Integer.MIN_VALUE
                || a == Integer.MIN_VALUE && b == -1 || a != res / b);
    }

    public static int checkedMultiply(int a, int b) {
        if (checkOverflow(a, b)) {
            throw new OverflowException(
                    String.format("overflow (calculate multiply) for '%d *df %d'", a, b)
            );
        }
        return a * b;
    }
}
