package games.moegirl.sinocraft.sinocore.event;

@FunctionalInterface
public interface ICancellableEventHandler<ARGS extends ICancellableEventArgs> extends IEventHandler<ARGS> {
}
