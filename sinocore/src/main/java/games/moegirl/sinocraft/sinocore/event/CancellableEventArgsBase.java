package games.moegirl.sinocraft.sinocore.event;

public abstract class CancellableEventArgsBase implements ICancellableEventArgs {
    private boolean isCancelled = false;

    @Override
    public void cancel() {
        isCancelled = true;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }
}
