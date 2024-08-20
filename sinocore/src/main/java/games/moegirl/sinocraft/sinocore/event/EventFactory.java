package games.moegirl.sinocraft.sinocore.event;

public class EventFactory {
    public static <A> IEvent<A, IEventHandler<A>> createEvent(Class<A> argsType) {
        return new EventImpl<>();
    }

    public static <A extends ICancellableArgs> IEvent<A, ICancellableEventHandler<A>> createCancellableEvent(Class<A> argsType) {
        return new EventImpl<>();
    }
}
