package expression;


/**
 * Three-argument arithmetic expression over integers.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface TripleExpression extends ToMiniString {

    int evaluate(int x, int y, int z);
}
