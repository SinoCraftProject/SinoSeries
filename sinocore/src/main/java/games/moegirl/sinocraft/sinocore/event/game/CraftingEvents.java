package games.moegirl.sinocraft.sinocore.event.game;

import games.moegirl.sinocraft.sinocore.event.EventFactory;
import games.moegirl.sinocraft.sinocore.event.ICancellableEventHandler;
import games.moegirl.sinocraft.sinocore.event.IEvent;
import games.moegirl.sinocraft.sinocore.event.game.args.crafting.CartographyTableCraftEventArgs;

public class CraftingEvents {
    public static final IEvent<CartographyTableCraftEventArgs, ICancellableEventHandler<CartographyTableCraftEventArgs>> CARTOGRAPHY_CRAFT = EventFactory.createCancellableEvent(CartographyTableCraftEventArgs.class);

}
