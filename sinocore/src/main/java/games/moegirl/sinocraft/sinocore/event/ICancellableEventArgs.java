package games.moegirl.sinocraft.sinocore.event;

public interface ICancellableEventArgs {
    void cancel();

    boolean isCancelled();
}
