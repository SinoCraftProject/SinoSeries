package games.moegirl.sinocraft.sinocore.event;

import java.util.List;

public interface IEvent<EVENT_ARGS, HANDLER extends IEventHandler<EVENT_ARGS>> {

    EVENT_ARGS invoke(EVENT_ARGS args);

    void register(HANDLER listener);

    void unregister(HANDLER listener);

    boolean isRegistered(HANDLER listener);

    List<HANDLER> getListeners();

    void clearListeners();
}
