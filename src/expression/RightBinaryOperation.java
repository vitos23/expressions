package expression;

public abstract class RightBinaryOperation extends BinaryOperation {
    public RightBinaryOperation(GeneralExpression left, GeneralExpression right) {
        super(left, right);
    }

    @Override
    protected boolean rightNeedBrackets() {
        return right instanceof BinaryOperation
                && ((BinaryOperation) right).getPriority() == getPriority();
    }
}
