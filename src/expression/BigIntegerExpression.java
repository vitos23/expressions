package expression;

import java.math.BigInteger;

/**
 * One-argument arithmetic expression over longs.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface BigIntegerExpression extends ToMiniString {

    BigInteger evaluate(BigInteger x);
}
