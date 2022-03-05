package expression;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class BinaryOperation implements GeneralExpression {
    protected GeneralExpression left;
    protected GeneralExpression right;

    public BinaryOperation(GeneralExpression left, GeneralExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int evaluate(int x) {
        return calculate(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return calculate(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    abstract protected int calculate(int a, int b);

    abstract protected BigDecimal calculate(BigDecimal a, BigDecimal b);

    @Override
    public String toString() {
        return "(" + left + " " + getOperationSymbol() + " " + right + ")";
        //return String.format("(%s %s %s)", left, getOperationSymbol(), right);
    }

    abstract protected String getOperationSymbol();

    @Override
    public boolean equals(Object obj) {
        return obj != null
                && this.getClass().equals(obj.getClass())
                && left.equals(((BinaryOperation) obj).left)
                && right.equals(((BinaryOperation) obj).right);
    }

    @Override
    public int hashCode() {
        //return (left.hashCode() * 17 + right.hashCode()) * 17 + getOperationSymbol().hashCode();
        return Objects.hash(left, right, getOperationSymbol());
    }

    abstract protected int getPriority();

    protected boolean leftNeedBrackets() {
        return false;
    }

    protected boolean rightNeedBrackets() {
        return false;
    }

    private boolean checkPriority(GeneralExpression e) {
        return e instanceof BinaryOperation
                && (((BinaryOperation) e).getPriority() < this.getPriority());
    }

    private String wrapString(String str, boolean brackets) {
        if (brackets) {
            return "(" + str + ")";
        } else {
            return str;
        }
    }

    @Override
    public String toMiniString() {
        return wrapString(left.toMiniString(), checkPriority(left) || leftNeedBrackets())
                + " " + getOperationSymbol() + " "
                + wrapString(right.toMiniString(), checkPriority(right) || rightNeedBrackets());
    }
}
