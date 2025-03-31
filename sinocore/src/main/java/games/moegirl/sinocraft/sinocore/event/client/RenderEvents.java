package games.moegirl.sinocraft.sinocore.event.client;

import games.moegirl.sinocraft.sinocore.event.EventFactory;
import games.moegirl.sinocraft.sinocore.event.ICancellableEventHandler;
import games.moegirl.sinocraft.sinocore.event.IEvent;
import games.moegirl.sinocraft.sinocore.event.IEventHandler;
import games.moegirl.sinocraft.sinocore.event.client.args.render.BeforeRenderHudArgs;
import games.moegirl.sinocraft.sinocore.event.client.args.render.CustomItemRendererArgs;
import games.moegirl.sinocraft.sinocore.event.client.args.render.RenderItemInFrameArgs;

public class RenderEvents {
    public static final IEvent<RenderItemInFrameArgs, ICancellableEventHandler<RenderItemInFrameArgs>> RENDER_ITEM_IN_FRAME = EventFactory.createCancellableEvent(RenderItemInFrameArgs.class);

    public static final IEvent<BeforeRenderHudArgs, IEventHandler<BeforeRenderHudArgs>> BEFORE_RENDER_HUD = EventFactory.createEvent(BeforeRenderHudArgs.class);

    public static final IEvent<CustomItemRendererArgs, IEventHandler<CustomItemRendererArgs> > CUSTOM_ITEM_RENDERER = EventFactory.createEvent(CustomItemRendererArgs.class);
}
