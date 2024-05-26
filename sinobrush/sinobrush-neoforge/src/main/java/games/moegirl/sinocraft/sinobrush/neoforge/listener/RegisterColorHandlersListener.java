package games.moegirl.sinocraft.sinobrush.neoforge.listener;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import net.minecraft.world.item.DyeableLeatherItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@Mod.EventBusSubscriber(modid = SinoBrush.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterColorHandlersListener {
    @SubscribeEvent
    public static void onRegisterColorHandlersEvent(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            if (tintIndex == 0 && stack.getItem() instanceof DyeableLeatherItem dyeable) {
                return dyeable.getColor(stack);
            }
            return -1;
        }, SBRItems.XUAN_PAPER.get(), SBRItems.FILLED_XUAN_PAPER.get(), SBRItems.INK_BOTTLE.get());
    }
}
