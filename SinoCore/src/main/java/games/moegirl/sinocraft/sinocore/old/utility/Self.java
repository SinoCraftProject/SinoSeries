package games.moegirl.sinocraft.sinocore.old.utility;

public interface Self<T> {

    default T self() {
        return (T) this;
    }
}
