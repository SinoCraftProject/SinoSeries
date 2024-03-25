package games.moegirl.sinocraft.sinocore.utility;

public interface ISelf<T> {

    default T self() {
        return (T) this;
    }
}
