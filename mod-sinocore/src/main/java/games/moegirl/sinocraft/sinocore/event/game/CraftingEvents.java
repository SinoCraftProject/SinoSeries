package games.moegirl.sinocraft.sinocore.event.game;

import games.moegirl.sinocraft.sinocore.event.EventFactory;
import games.moegirl.sinocraft.sinocore.event.ICancellableEventHandler;
import games.moegirl.sinocraft.sinocore.event.IEvent;
import games.moegirl.sinocraft.sinocore.event.game.args.crafting.CartographyTableCraftArgs;

public class CraftingEvents {
    public static final IEvent<CartographyTableCraftArgs, ICancellableEventHandler<CartographyTableCraftArgs>> CARTOGRAPHY_CRAFT = EventFactory.createCancellableEvent(CartographyTableCraftArgs.class);

}
