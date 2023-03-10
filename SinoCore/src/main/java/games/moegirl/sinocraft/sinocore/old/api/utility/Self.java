package games.moegirl.sinocraft.sinocore.old.api.utility;

public interface Self<T> {

    default T self() {
        return (T) this;
    }
}
