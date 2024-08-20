package games.moegirl.sinocraft.sinocore.event;

public abstract class CancellableArgsBase implements ICancellableArgs {
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
