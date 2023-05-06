package games.moegirl.sinocraft.sinocore.event;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockStrippingEvent {
    @SubscribeEvent
    public static void onBlockStripping(BlockEvent.BlockToolModificationEvent event) {
        // Todo: qyl27: stripping wood block. maybe from tree registry?
//        if (event.getToolAction() == ToolActions.AXE_STRIP) {
//
//        }
    }
}
