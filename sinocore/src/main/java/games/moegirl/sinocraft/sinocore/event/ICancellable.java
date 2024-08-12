package games.moegirl.sinocraft.sinocore.event;

public interface ICancellable {
    default void cancel() {
        throw EventCancelException.INSTANCE;
    }
}
