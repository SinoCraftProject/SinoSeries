package games.moegirl.sinocraft.sinocore.utility.decorator;

import java.util.function.UnaryOperator;

public class StringDecorator implements IDecoratorChain<String> {
    protected UnaryOperator<String> operator;

    protected StringDecorator innerDecorator;

    public StringDecorator(UnaryOperator<String> operator) {
        this.operator = operator;
    }

    public StringDecorator(UnaryOperator<String> operator, StringDecorator innerDecorator) {
        this.operator = operator;
        this.innerDecorator = innerDecorator;
    }

    public boolean hasChild() {
        return innerDecorator != null;
    }

    public StringDecorator getChild() {
        return innerDecorator;
    }

    @Override
    public String decorate(String s) {
        return operator.apply(s);
    }

    public static StringDecorator noOp() {
        return new StringDecorator(s -> s);
    }

    public static StringDecorator appendBack(String inserter) {
        return new StringDecorator(s -> s + inserter);
    }

    public static StringDecorator appendFront(String inserter) {
        return new StringDecorator(s -> inserter + s);
    }

    public static StringDecorator insertMiddle(String front, String back) {
        return new StringDecorator(s -> front + s + back);
    }
}
