package games.moegirl.sinocraft.sinocore.util;

public interface ISelf<T> {

    default T self() {
        return (T) this;
    }
}
