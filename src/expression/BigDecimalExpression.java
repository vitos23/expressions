package expression;


import java.math.BigDecimal;

/**
 * One-argument arithmetic expression over longs.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface BigDecimalExpression extends ToMiniString {

    BigDecimal evaluate(BigDecimal x);
}
