package expression;

import java.math.BigDecimal;

public class Const implements GeneralExpression {
    private final Number value;

    public Const(Number number) {
        value = number;
    }

    public Const(int number) {
        value = number;
    }

    public Const(BigDecimal number) {
        value = number;
    }

    @Override
    public int evaluate(int x) {
        return value.intValue();
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass().equals(obj.getClass())
                && value.equals(((Const) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return value instanceof BigDecimal ? (BigDecimal) value : new BigDecimal(value.intValue());
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value.intValue();
    }
}
