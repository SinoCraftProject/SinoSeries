package games.moegirl.sinocraft.sinocore.event.client;

import games.moegirl.sinocraft.sinocore.event.EventFactory;
import games.moegirl.sinocraft.sinocore.event.ICancellableEventHandler;
import games.moegirl.sinocraft.sinocore.event.IEvent;
import games.moegirl.sinocraft.sinocore.event.client.args.render.RenderItemInFrameArgs;

public class RenderEvents {
    public static final IEvent<RenderItemInFrameArgs, ICancellableEventHandler<RenderItemInFrameArgs>> RENDER_ITEM_IN_FRAME = EventFactory.createCancellableEvent(RenderItemInFrameArgs.class);

}
