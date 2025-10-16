package games.moegirl.sinocraft.sinocore.event.client;

import games.moegirl.sinocraft.sinocore.event.EventFactory;
import games.moegirl.sinocraft.sinocore.event.IEvent;
import games.moegirl.sinocraft.sinocore.event.IEventHandler;
import games.moegirl.sinocraft.sinocore.event.client.args.model.AfterBakeArgs;

public class ModelEvents {
    public static final IEvent<AfterBakeArgs, IEventHandler<AfterBakeArgs>> AFTER_BAKE = EventFactory.createEvent(AfterBakeArgs.class);

}
