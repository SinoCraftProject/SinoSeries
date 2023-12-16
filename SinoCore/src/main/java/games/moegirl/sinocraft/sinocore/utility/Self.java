package games.moegirl.sinocraft.sinocore.utility;

/**
 * 问：如何证明 我是我
 */
public interface Self<T> {

    default T self() {
        return (T) this;
    }

    default <TYPE_AS> TYPE_AS selfAs() {
        return (TYPE_AS) this;
    }
}
