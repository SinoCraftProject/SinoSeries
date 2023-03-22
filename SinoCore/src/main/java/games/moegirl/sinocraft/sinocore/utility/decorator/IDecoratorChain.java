package games.moegirl.sinocraft.sinocore.utility.decorator;

import java.util.function.UnaryOperator;

public interface IDecoratorChain<T> extends UnaryOperator<T> {
    boolean hasChild();

    IDecoratorChain<T> getChild();

    @Override
    default T apply(T t) {
        T result = t;
        if (hasChild()) {
            result = getChild().apply(t);
        }

        return decorate(result);
    }

    T decorate(T t);
}
