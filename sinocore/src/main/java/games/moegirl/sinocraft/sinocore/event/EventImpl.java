package games.moegirl.sinocraft.sinocore.event;

import java.util.List;
import java.util.PriorityQueue;

public class EventImpl<ARGS, HANDLER extends IEventHandler<ARGS>> implements IEvent<ARGS, HANDLER>  {

    protected final PriorityQueue<HANDLER> listeners = new PriorityQueue<>();

    @Override
    public ARGS invoke(ARGS eventArgs) {
        for (var listener : listeners) {
            listener.accept(eventArgs);
        }
        return eventArgs;
    }

    @Override
    public void register(HANDLER listener) {
        listeners.add(listener);
    }

    @Override
    public void unregister(HANDLER listener) {
        listeners.remove(listener);
    }

    @Override
    public boolean isRegistered(HANDLER listener) {
        return listeners.contains(listener);
    }

    @Override
    public List<HANDLER> getListeners() {
        return listeners.stream().toList();
    }

    @Override
    public void clearListeners() {
        listeners.clear();
    }
}
