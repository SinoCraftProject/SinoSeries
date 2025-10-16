package games.moegirl.sinocraft.sinocore.event;

public interface ICancellableArgs {
    void cancel();

    boolean isCancelled();
}
