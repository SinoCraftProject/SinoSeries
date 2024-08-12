package games.moegirl.sinocraft.sinocore.event;

@FunctionalInterface
public interface ICancellableEventHandler<ARGS> extends IEventHandler<ARGS>, ICancellable {
}
