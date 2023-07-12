package games.moegirl.sinocraft.sinodivination.handler;

import games.moegirl.sinocraft.sinodivination.block.CotinusBlock;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() instanceof CotinusBlock block && !block.isAllowed(event.getPos(), event.getPlayer(), false)) {
            event.setCanceled(true);
        }
    }
}
