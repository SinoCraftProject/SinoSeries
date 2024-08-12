package games.moegirl.sinocraft.sinocore.event.game;

import games.moegirl.sinocraft.sinocore.event.EventFactory;
import games.moegirl.sinocraft.sinocore.event.ICancellableEventHandler;
import games.moegirl.sinocraft.sinocore.event.IEvent;
import games.moegirl.sinocraft.sinocore.event.game.args.CartographyCraftEventArgs;

public class CraftingEvents {
    public static final IEvent<CartographyCraftEventArgs, ICancellableEventHandler<CartographyCraftEventArgs>> CARTOGRAPHY_CRAFT = EventFactory.createCancellableEvent(CartographyCraftEventArgs.class);

}
